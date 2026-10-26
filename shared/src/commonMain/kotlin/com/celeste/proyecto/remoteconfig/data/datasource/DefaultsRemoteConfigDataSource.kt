package com.celeste.proyecto.remoteconfig.data.datasource

class DefaultsRemoteConfigDataSource : RemoteConfigDataSource {
    override suspend fun fetchAndActivate(): Result<Unit> = Result.success(Unit)

    override fun getString(key: String): String =
        (RemoteConfigDefaults.values[key] as? String) ?: ""

    override fun getBoolean(key: String): Boolean =
        (RemoteConfigDefaults.values[key] as? Boolean) ?: false

    override fun getLong(key: String): Long =
        ((RemoteConfigDefaults.values[key] as? Number)?.toLong()) ?: 0L
}
