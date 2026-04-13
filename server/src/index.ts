import express from 'express';
import http from 'http';
import { WebSocketServer } from 'ws';
import cors from 'cors';
import dotenv from 'dotenv';
import sessionRoutes from './routes/session.routes.js';
import { initDb } from './models/db.js';
import { STTService } from './services/stt.service.js';

dotenv.config();

const app = express();
const server = http.createServer(app);
const wss = new WebSocketServer({ server });
const sttService = new STTService();
const port = process.env.PORT || 3000;

// Initialize Database
initDb();

// Middleware
app.use(express.json());
app.use(cors());

// WebSocket real-time bridge
wss.on('connection', (ws) => {
    console.log('[MindSpace] Client connected for Voice Streaming');
    
    let audioStream: any = null;

    ws.on('message', (message, isBinary) => {
        if (isBinary) {
            if (!audioStream) {
                audioStream = sttService.createStream((text: string, isFinal: boolean) => {
                    ws.send(JSON.stringify({ type: 'transcription', text, isFinal }));
                });
            }
            audioStream.write(message);
        }
    });

    ws.on('close', () => {
        console.log('[MindSpace] Voice stream closed');
        if (audioStream) {
            audioStream.end();
            audioStream = null;
        }
    });
});

// Routes
app.use('/api/sessions', sessionRoutes);

// Health check
app.get('/health', (req, res) => {
    res.json({ status: 'active', app: 'MindSpace', version: '1.0.0' });
});

server.listen(port, () => {
    console.log(`[MindSpace] Server running at http://localhost:${port}`);
});
