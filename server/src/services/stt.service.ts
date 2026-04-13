import { v2 } from '@google-cloud/speech';
import { Writable } from 'stream';

// STT standard: 16kHz, Mono, 16-bit PCM
const SAMPLE_RATE = 16000;

export class STTService {
  private client: v2.SpeechClient;
  private projectId: string;

  constructor() {
    this.client = new v2.SpeechClient({
        apiEndpoint: 'us-central1-speech.googleapis.com' // Chirp 3 is regional
    });
    this.projectId = process.env.GOOGLE_PROJECT_ID || 'mindspace-app';
  }

  /**
   * Creates a bidirectional recognition stream.
   * @param onTranscription Callback for interim and final results
   */
  public createStream(onTranscription: (text: string, isFinal: boolean) => void): Writable {
    // Wildcard recognizer for transient config
    const recognizer = `projects/${this.projectId}/locations/us-central1/recognizers/_`;

    const stream = this.client._streamingRecognize()
      .on('error', (err: any) => {
        console.error('STT Stream Error:', err);
      })
      .on('data', (response: any) => {
        const result = response.results?.[0];
        if (result) {
            const transcript = result.alternatives?.[0]?.transcript || '';
            const isFinal = result.isFinal || false;
            onTranscription(transcript, isFinal);
        }
      });

    // Send initial config
    stream.write({
        recognizer: recognizer,
        streamingConfig: {
            config: {
                autoDecodingConfig: {},
                model: 'chirp_3',
                languageCodes: ['en-US'],
            },
            streamingFeatures: {
                interimResults: true,
            }
        }
    });

    // Return a wrapper that extracts the audio chunks from buffers
    const audioStream = new Writable({
        write(chunk, encoding, callback) {
            stream.write({ audio: chunk });
            callback();
        },
        final(callback) {
            stream.end();
            callback();
        }
    });

    return audioStream;
  }
}
