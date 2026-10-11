package com.freesculpt.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class LocalAiAgent(
    private val baseUrl: String = "http://127.0.0.1:11434",
    private val model: String = "hermes",
) {
    suspend fun generateAdvice(prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/api/generate")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true

            val payload = JSONObject().apply {
                put("model", model)
                put("prompt", prompt)
                put("stream", false)
            }

            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(payload.toString())
            writer.flush()
            writer.close()

            val responseCode = conn.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return@withContext "AI model unavailable. Check local model server."
            }

            val raw = conn.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(raw)
            json.optString("response", "No response available from AI model.").trim()
        } catch (_: Exception) {
            "Local AI not reachable. Start Hermes or Ollama on the device and retry."
        }
    }
}
