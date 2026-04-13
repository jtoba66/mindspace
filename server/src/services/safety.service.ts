/**
 * Safety Service: The first layer of the MindSpace AI Pipeline.
 * Responsible for detecting crisis language and immediate risks.
 */

const CRISIS_KEYWORDS = [
    'kill myself', 'suicide', 'self harm', 'don\'t want to live',
    'end it all', 'hurt myself', 'hopeless', 'no way out'
];

export interface SafetyResult {
    isSafe: boolean;
    reason?: string;
    resources?: string[];
}

export const checkSafety = (input: string): SafetyResult => {
    const serializedInput = input.toLowerCase();
    
    const matchedKeyword = CRISIS_KEYWORDS.find(keyword => serializedInput.includes(keyword));
    
    if (matchedKeyword) {
        return {
            isSafe: false,
            reason: 'Crisis language detected.',
            resources: [
                '988 Suicide & Crisis Lifeline (Call or Text 988)',
                'Crisis Text Line (Text HOME to 741741)',
                'National Domestic Violence Hotline (1-800-799-7233)'
            ]
        };
    }

    return { isSafe: true };
};
