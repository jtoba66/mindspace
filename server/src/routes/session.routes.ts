import { Router } from 'express';
import { processBrainDump, getSessions } from '../controllers/session.controller.js';

const router = Router();

router.post('/process', processBrainDump);
router.get('/', getSessions);

export default router;
