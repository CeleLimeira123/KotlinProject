package com.celeste.proyecto

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CelesteMessagingService : FirebaseMessagingService() {

    // Con la app abierta (o con mensajes "data") llega aquí y la mostramos nosotros.
    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title
            ?: message.data["title"]
            ?: getString(R.string.app_name)
        val body = message.notification?.body
            ?: message.data["body"]
            ?: return
        NotificationHelper.show(this, title, body)
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "Nuevo token: $token")
    }
}