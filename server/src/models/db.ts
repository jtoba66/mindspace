import Database from 'better-sqlite3';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const dbPath = path.resolve(__dirname, '../../mindspace.db');
const db = new Database(dbPath);

// Initialize schema
export const initDb = () => {
    // Sessions table
    db.prepare(`
        CREATE TABLE IF NOT EXISTS sessions (
            id TEXT PRIMARY KEY,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            intent TEXT,
            energy TEXT,
            raw_input TEXT,
            is_private INTEGER DEFAULT 0
        )
    `).run();

    // Plans table
    db.prepare(`
        CREATE TABLE IF NOT EXISTS plans (
            id TEXT PRIMARY KEY,
            session_id TEXT,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            plan_json TEXT,
            FOREIGN KEY(session_id) REFERENCES sessions(id) ON DELETE CASCADE
        )
    `).run();

    // Emotional themes table (for Continuity)
    db.prepare(`
        CREATE TABLE IF NOT EXISTS emotional_themes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            session_id TEXT,
            theme_text TEXT,
            detected_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY(session_id) REFERENCES sessions(id) ON DELETE CASCADE
        )
    `).run();

    console.log('SQLite database initialized at:', dbPath);
};

export default db;
