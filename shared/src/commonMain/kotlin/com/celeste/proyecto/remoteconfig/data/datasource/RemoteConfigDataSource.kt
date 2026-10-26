package com.celeste.proyecto.remoteconfig.data.datasource

interface RemoteConfigDataSource {
    suspend fun fetchAndActivate(): Result<Unit>
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getLong(key: String): Long
}
