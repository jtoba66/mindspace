import OpenAI from 'openai';
import dotenv from 'dotenv';

dotenv.config();

const openai = new OpenAI({
    apiKey: process.env.OPENAI_API_KEY || 'MISSING_API_KEY',
});

export interface Classification {
    intent: 'planning' | 'emotional' | 'mixed';
    energy: 'low' | 'steady' | 'ready';
    sentiment: string;
    themes: string[];
}

export const classifyInput = async (input: string): Promise<Classification> => {
    try {
        const response = await openai.chat.completions.create({
            model: "gpt-4o-mini", // Cost-effective, fast
            messages: [
                {
                    role: "system",
                    content: `You are the Classifier for MindSpace. Your job is to analyze a "brain dump" from an overwhelmed user and return a structured classification.
                    
                    Intents:
                    - planning: focused on tasks, to-dos, logistics.
                    - emotional: focused on feelings, venting, rants.
                    - mixed: both tasks and feelings present.
                    
                    Energy:
                    - low: sounds exhausted, defeated, or very quiet.
                    - steady: moderate energy, stable.
                    - ready: high energy, sounds geared up or impatient to act.
                    
                    Return ONLY JSON in this format:
                    {
                        "intent": "planning" | "emotional" | "mixed",
                        "energy": "low" | "steady" | "ready",
                        "sentiment": "one word summary of mood",
                        "themes": ["list of 2-3 main topics mentioned"]
                    }`
                },
                {
                    role: "user",
                    content: input
                }
            ],
            response_format: { type: "json_object" }
        });

        const result = JSON.parse(response.choices[0].message.content || '{}');
        return {
            intent: result.intent || 'mixed',
            energy: result.energy || 'steady',
            sentiment: result.sentiment || 'neutral',
            themes: result.themes || []
        };
    } catch (error) {
        console.error('Classification Layer Error:', error);
        return {
            intent: 'mixed',
            energy: 'steady',
            sentiment: 'unknown',
            themes: []
        };
    }
};
