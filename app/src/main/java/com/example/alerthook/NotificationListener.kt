
package com.example.alerthook

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.let {
            val packageName = it.packageName
            val notification = it.notification
            val title = notification.extras.getString("android.title")
            val text = notification.extras.getString("android.text")

            Log.d("NotificationListener", "Notification Posted: ")
            Log.d("NotificationListener", "Package: $packageName")
            Log.d("NotificationListener", "Title: $title")
            Log.d("NotificationListener", "Text: $text")
            // Aqui você adicionaria a lógica para processar a notificação e enviá-la para o webhook
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        sbn?.let {
            Log.d("NotificationListener", "Notification Removed: ${it.packageName}")
        }
    }
}
