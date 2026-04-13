import { Request, Response } from 'express';
import { v4 as uuidv4 } from 'uuid';
import db from '../models/db.js';
import { checkSafety } from '../services/safety.service.js';
import { classifyInput } from '../services/classifier.service.js';
import { generateCoreResponse } from '../services/claude.service.js';

export const processBrainDump = async (req: Request, res: Response) => {
    const { input, isPrivate, energyOverride } = req.body;

    if (!input) {
        return res.status(400).json({ error: 'Input is required.' });
    }

    try {
        // 1. Safety Layer
        const safety = checkSafety(input);
        if (!safety.isSafe) {
            return res.json({
                type: 'safety_trigger',
                header: 'We hear you.',
                sections: [{ title: 'You are not alone', icon: 'heart', items: safety.resources }],
                closing: 'Please reach out to one of these resources.'
            });
        }

        // 2. Classification Layer
        console.log('[MindSpace] Starting Classification Layer...');
        const classification = await Promise.race([
            classifyInput(input),
            new Promise<never>((_, reject) => setTimeout(() => reject(new Error('Classification timed out')), 15000))
        ]);
        console.log('[MindSpace] Classification Complete:', classification.intent, '/', classification.energy);
        
        // Use energy override if provided (from the Energy Check-in screen)
        if (energyOverride) {
            classification.energy = energyOverride;
        }

        // 3. Core Response Layer (Claude)
        console.log('[MindSpace] Starting Core Response Layer...');
        const output = await Promise.race([
            generateCoreResponse(input, classification),
            new Promise<never>((_, reject) => setTimeout(() => reject(new Error('Core Response timed out')), 15000))
        ]);
        console.log('[MindSpace] Core Response Generated Successfully');

        const sessionId = uuidv4();

        // 4. Persistence (Respect Private Mode)
        if (!isPrivate) {
            const sessionStmt = db.prepare(`
                INSERT INTO sessions (id, intent, energy, raw_input, is_private)
                VALUES (?, ?, ?, ?, ?)
            `);
            sessionStmt.run(sessionId, classification.intent, classification.energy, input, 0);

            const planStmt = db.prepare(`
                INSERT INTO plans (id, session_id, plan_json)
                VALUES (?, ?, ?)
            `);
            planStmt.run(uuidv4(), sessionId, JSON.stringify(output));

            // Store themes
            const themeStmt = db.prepare(`
                INSERT INTO emotional_themes (session_id, theme_text)
                VALUES (?, ?)
            `);
            classification.themes.forEach(theme => {
                themeStmt.run(sessionId, theme);
            });
        }

        // Return the final clarity/validation
        res.json({
            ...output,
            sessionId: isPrivate ? null : sessionId,
            classification: {
                intent: classification.intent,
                energy: classification.energy
            }
        });

    } catch (error: any) {
        console.error('Session Controller Error:', error);
        res.status(500).json({ error: 'Failed to process session.', details: error.message });
    }
};

export const getSessions = (req: Request, res: Response) => {
    try {
        const rows = db.prepare(`
            SELECT s.*, p.plan_json 
            FROM sessions s
            JOIN plans p ON s.id = p.session_id
            ORDER BY s.created_at DESC
        `).all();
        
        const sessions = rows.map((row: any) => ({
            ...row,
            plan: JSON.parse(row.plan_json)
        }));
        
        res.json(sessions);
    } catch (error) {
        res.status(500).json({ error: 'Failed to retrieve sessions.' });
    }
};
