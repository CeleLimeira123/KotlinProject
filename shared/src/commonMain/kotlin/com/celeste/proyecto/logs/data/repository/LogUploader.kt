package com.celeste.proyecto.logs.data.repository

interface LogUploader {
    suspend fun uploadLog(level: String, message: String, userId: String = ""): Result<Unit>
}
