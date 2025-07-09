
package com.example.alerthook

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationListener : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var settingsManager: SettingsManager
    private lateinit var webhookService: WebhookService

    override fun onCreate() {
        super.onCreate()
        settingsManager = SettingsManager(applicationContext)
        webhookService = WebhookService()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.let {
            val packageName = it.packageName
            val notification = it.notification
            val title = notification.extras.getString("android.title")
            val text = notification.extras.getString("android.text")
            val postTime = it.postTime

            scope.launch {
                val selectedApps = settingsManager.selectedApps.first()
                val webhookUrl = settingsManager.webhookUrl.first()

                if (selectedApps.contains(packageName)) {
                    Log.d("NotificationListener", "Notification Posted: ")
                    Log.d("NotificationListener", "Package: $packageName")
                    Log.d("NotificationListener", "Title: $title")
                    Log.d("NotificationListener", "Text: $text")

                    if (webhookUrl.isNotBlank()) {
                        val appName = try {
                            packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0)).toString()
                        } catch (e: Exception) {
                            packageName
                        }

                        val notificationPayload = NotificationPayload(
                            packageName = packageName,
                            appName = appName,
                            title = title,
                            text = text,
                            timestamp = postTime
                        )

                        val success = webhookService.sendNotification(webhookUrl, notificationPayload)
                        if (success) {
                            Log.d("NotificationListener", "Notification sent to webhook successfully!")
                        } else {
                            Log.e("NotificationListener", "Failed to send notification to webhook.")
                        }
                    } else {
                        Log.w("NotificationListener", "Webhook URL is not configured.")
                    }
                } else {
                    Log.d("NotificationListener", "Notification from unselected app: $packageName")
                }
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        sbn?.let {
            Log.d("NotificationListener", "Notification Removed: ${it.packageName}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
