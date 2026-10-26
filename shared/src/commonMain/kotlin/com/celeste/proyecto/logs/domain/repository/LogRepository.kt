package com.celeste.proyecto.logs.domain.repository

interface LogRepository {
    suspend fun sendLog(level: String, message: String, userId: String = ""): Result<Unit>
}
