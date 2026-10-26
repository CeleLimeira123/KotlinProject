package com.celeste.proyecto.scheduler

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.celeste.proyecto.catalog.domain.usecase.GetCatalogMoviesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val getCatalogMoviesUseCase: GetCatalogMoviesUseCase by inject()

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Ejecutando sincronización periódica en segundo plano...")
            getCatalogMoviesUseCase()
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error en sincronización periódica: ${e.message}", e)
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
        Log.d("SyncScheduler", "Ejecutando syncNow()...")
    }

    private companion object {
        const val WORK_NAME = "celeste_catalog_sync_work"
    }
}
