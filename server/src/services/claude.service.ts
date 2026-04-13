import Anthropic from '@anthropic-ai/sdk';
import dotenv from 'dotenv';
import { Classification } from './classifier.service.js';

dotenv.config();

const anthropic = new Anthropic({
    apiKey: process.env.ANTHROPIC_API_KEY || 'MISSING_API_KEY',
});

export const generateCoreResponse = async (input: string, classification: Classification) => {
    // Instant fallback if no key
    if (process.env.ANTHROPIC_API_KEY === undefined || process.env.ANTHROPIC_API_KEY.includes('your_anthropic')) {
        console.log('[MindSpace] Using mock response (Missing API Key)');
        return {
            type: "plan",
            header: "Let's find some space.",
            sections: [
                {
                    title: "Focus on This First",
                    icon: "target",
                    items: ["Take five slow breaths right here.", "Clear one small surface near you."]
                },
                {
                    title: "Gentle Next Steps",
                    icon: "leaf",
                    items: ["Drink a glass of water.", "Write down one thing that went okay today."]
                },
                {
                    title: "Rest for Later",
                    icon: "moon",
                    items: ["The bigger decisions can wait until tomorrow.", "Check back in when you feel ready."]
                }
            ],
            closing: "You don't have to carry it all at once."
        };
    }

    const isEmotional = classification.intent === 'emotional';
    
    const systemPrompt = `You are MindSpace, a wise, quiet, and empathetic companion for women who are feeling crushed by life.
    
    Current User State:
    - Intent: ${classification.intent}
    - Energy: ${classification.energy}
    - Themes: ${classification.themes.join(', ')}
    
    Your Tone:
    - Wise friend, not a therapist.
    - Understated empathy ("I hear you" or "Tough day"), not fake robotic excitement.
    - Short sentences.
    - Gentle, never clinical.
    
    If intent is 'emotional': 
    - Focus on Mirroring and Soft Care. 
    - Validate the feeling. Don't force a task list.
    
    If intent is 'planning' or 'mixed':
    - Focus on Clarity.
    - Use the "Folding" method: Even if they have many tasks, only pick the top 2.
    - Bucket items into: "Focus on This First", "Gentle Next Steps", and "Rest for Later".
    
    Response Structure (Mandatory JSON):
    {
      "type": "plan" | "validation",
      "header": "A warm, short header",
      "sections": [
        {
          "title": "Bucket Title",
          "icon": "icon_name",
          "items": ["list of strings"]
        }
      ],
      "closing": "A final warm thought"
    }`;

    try {
        const response = await anthropic.messages.create({
            model: "claude-3-5-sonnet-20240620",
            max_tokens: 1024,
            system: systemPrompt,
            messages: [{ role: "user", content: input }]
        });

        // The formatting layer (Layer 3) is effectively handled here by the JSON prompt
        // but we would typically parse/validate it.
        const content = response.content[0].type === 'text' ? response.content[0].text : '{}';
        return JSON.parse(content);
    } catch (error) {
        console.error('Core Response Layer Error:', error);
        return {
            type: "validation",
            header: "Tough moment.",
            sections: [{ title: "Soft care", icon: "leaf", items: ["Just breathe for a second. We'll try again when things feel a bit quieter."] }],
            closing: "I'm still here."
        };
    }
};
