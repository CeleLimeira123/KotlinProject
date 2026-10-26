package com.celeste.proyecto.logs.data.repository

class IosLogUploader : LogUploader {
    override suspend fun uploadLog(level: String, message: String, userId: String): Result<Unit> {
        return Result.success(Unit)
    }
}
