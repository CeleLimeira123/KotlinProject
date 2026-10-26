package com.celeste.proyecto.logs.data.repository

class IosLogUploader : LogUploader {
    override suspend fun upload(message: String, stackTrace: String): Boolean {
        return true
    }
}
