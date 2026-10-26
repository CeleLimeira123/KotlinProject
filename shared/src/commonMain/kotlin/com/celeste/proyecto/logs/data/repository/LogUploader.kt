package com.celeste.proyecto.logs.data.repository

interface LogUploader {
    suspend fun upload(message: String, stackTrace: String = ""): Boolean
}
