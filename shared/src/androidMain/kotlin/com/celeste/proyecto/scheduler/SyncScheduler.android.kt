package com.celeste.proyecto.scheduler

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.celeste.proyecto.catalog.domain.usecase.GetCatalogMoviesUseCase
import com.celeste.proyecto.logs.domain.repository.LogRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val getCatalogMoviesUseCase: GetCatalogMoviesUseCase by inject()
    private val logRepository: LogRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Ejecutando refresco del catálogo en segundo plano...")
            getCatalogMoviesUseCase()

            // Intento de envío de log gRPC (no debe hacer fallar el worker si este falla)
            try {
                val logResult = logRepository.sendLog("Sincronización periódica completada")
                logResult.fold(
                    onSuccess = {
                        Log.d("SyncWorker", "Log enviado por gRPC correctamente")
                    },
                    onFailure = { error ->
                        Log.w("SyncWorker", "No se pudo enviar el log por gRPC: ${error.message}")
                    },
                )
            } catch (logErr: Exception) {
                Log.w("SyncWorker", "No se pudo enviar el log por gRPC: ${logErr.message}")
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error al refrescar el catálogo: ${e.message}", e)
            Result.retry()
        }
    }
}

actual class SyncScheduler(
    private val context: Context,
) {
    actual fun schedulePeriodicalSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest,
        )
        Log.d("SyncScheduler", "Sincronización periódica programada con WorkManager (KEEP).")
    }

    actual fun syncNow() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(oneTimeRequest)
        Log.d("SyncScheduler", "Sincronización única encolada con WorkManager (syncNow).")
    }

    private companion object {
        const val WORK_NAME = "celeste_catalog_sync_work"
    }
}
