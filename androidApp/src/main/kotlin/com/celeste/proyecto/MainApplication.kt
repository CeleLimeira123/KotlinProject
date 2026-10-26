package com.celeste.proyecto

import android.app.Application
import android.util.Log
import com.celeste.proyecto.di.initKoin
import com.celeste.proyecto.logs.data.LogsRemoteDataSource
import com.celeste.proyecto.logs.data.repository.LogUploader
import com.celeste.proyecto.scheduler.SyncScheduler
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val androidConfigModule = module {
            single(named("TMDB_API_KEY")) { BuildConfig.TMDB_API_KEY }
            single<LogUploader> {
                LogsRemoteDataSource(
                    context = this@MainApplication,
                    host = BuildConfig.GRPC_HOST,
                    port = BuildConfig.GRPC_PORT.toIntOrNull() ?: 9090,
                    usePlaintext = BuildConfig.GRPC_USE_PLAINTEXT,
                )
            }
        }

        initKoin {
            androidContext(this@MainApplication)
            modules(androidConfigModule)
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
