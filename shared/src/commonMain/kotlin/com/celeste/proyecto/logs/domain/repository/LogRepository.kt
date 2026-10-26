package com.celeste.proyecto.logs.domain.repository

interface LogRepository {
    suspend fun sendLog(message: String, stackTrace: String = ""): Result<Unit>
}
