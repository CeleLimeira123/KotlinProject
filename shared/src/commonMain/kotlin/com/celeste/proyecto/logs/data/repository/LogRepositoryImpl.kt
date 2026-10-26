package com.celeste.proyecto.logs.data.repository

import com.celeste.proyecto.logs.domain.repository.LogRepository

class LogRepositoryImpl(
    private val logUploader: LogUploader,
) : LogRepository {
    override suspend fun sendLog(message: String, stackTrace: String): Result<Unit> {
        return if (logUploader.upload(message, stackTrace)) {
            Result.success(Unit)
        } else {
            Result.failure(RuntimeException("Error al enviar log gRPC"))
        }
    }
}
