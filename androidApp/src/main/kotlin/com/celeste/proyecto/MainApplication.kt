package com.celeste.proyecto

import android.app.Application
import android.util.Log
import com.celeste.proyecto.di.initKoin
import com.celeste.proyecto.scheduler.SyncScheduler
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
        }
        NotificationHelper.createChannel(this)

        try {
            SyncScheduler(this).schedulePeriodicalSync()
        } catch (e: Exception) {
            Log.w("MainApplication", "Error al programar SyncScheduler: ${e.message}")
        }

        logPushToken()
    }

    private fun logPushToken() {
        if (FirebaseApp.getApps(this).isEmpty()) return
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Token: ${task.result}")
            } else {
                Log.w("FCM", "No se pudo obtener el token", task.exception)
            }
        }
    }
}
