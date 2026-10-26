package com.celeste.proyecto.logs.data.repository

import android.util.Log

class GrpcLogUploader(
    private val host: String = "10.0.2.2",
    private val port: Int = 50051,
) : LogUploader {
    override suspend fun uploadLog(level: String, message: String, userId: String): Result<Unit> {
        return try {
            Log.d("GrpcLogUploader", "Enviando log gRPC [$level] a $host:$port -> $message (UserId: $userId)")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w("GrpcLogUploader", "Fallo al enviar log gRPC: ${e.message}")
            Result.failure(e)
        }
    }
}
