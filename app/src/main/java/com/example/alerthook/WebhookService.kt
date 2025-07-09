package com.example.alerthook

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class NotificationPayload(
    val packageName: String?,
    val appName: String?,
    val title: String?,
    val text: String?,
    val timestamp: Long
)

class WebhookService {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun testWebhook(url: String): Boolean {
        return try {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("test" to "This is a test notification from AlertHook"))
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun sendNotification(url: String, payload: NotificationPayload): Boolean {
        return try {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}