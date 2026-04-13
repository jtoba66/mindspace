package com.mindspace.app.audio

import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.toByteString
import org.json.JSONObject

class VoiceStreamManager(
    private val onTranscriptionReceived: (String, Boolean) -> Unit
) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        // 10.0.2.2 is the special IP that allows Android emulators to access localhost
        val request = Request.Builder().url("ws://10.0.2.2:3000").build()
        
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val json = JSONObject(text)
                    if (json.getString("type") == "transcription") {
                        val transcript = json.getString("text")
                        val isFinal = json.getBoolean("isFinal")
                        onTranscriptionReceived(transcript, isFinal)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                t.printStackTrace()
            }
        })
    }

    fun streamAudio(data: ByteArray, length: Int) {
        val byteString = data.toByteString(0, length)
        webSocket?.send(byteString)
    }

    fun disconnect() {
        webSocket?.close(1000, "Recording stopped")
        webSocket = null
    }
}
