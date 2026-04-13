package com.mindspace.app.audio

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AudioRecorder(private val context: Context) {
    private var audioRecord: AudioRecord? = null
    private var isRecording = false

    // Chirp 3 / STT standard: 16kHz, Mono, 16-bit PCM
    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    suspend fun startRecording(outputFile: File) = withContext(Dispatchers.IO) {
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        audioRecord?.startRecording()
        isRecording = true

        val os = FileOutputStream(outputFile)
        val data = ByteArray(bufferSize)

        while (isRecording) {
            val read = audioRecord?.read(data, 0, bufferSize) ?: 0
            if (read > 0) {
                os.write(data, 0, read)
            }
        }

        os.close()
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}
