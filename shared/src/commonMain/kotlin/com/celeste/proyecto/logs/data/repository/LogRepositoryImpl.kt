package com.celeste.proyecto.logs.data.repository

import com.celeste.proyecto.logs.domain.repository.LogRepository

class LogRepositoryImpl(
    private val logUploader: LogUploader,
) : LogRepository {
    override suspend fun sendLog(level: String, message: String, userId: String): Result<Unit> {
        return logUploader.uploadLog(level, message, userId)
    }
}
