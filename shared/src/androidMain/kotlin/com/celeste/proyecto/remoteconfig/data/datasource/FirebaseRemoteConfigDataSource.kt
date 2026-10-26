package com.celeste.proyecto.remoteconfig.data.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseRemoteConfigDataSource(
    private val context: Context,
) : RemoteConfigDataSource {

    private val remoteConfig: FirebaseRemoteConfig? by lazy {
        if (FirebaseApp.getApps(context).isEmpty()) {
            null
        } else {
            FirebaseRemoteConfig.getInstance().also { config ->
                config.setConfigSettingsAsync(
                    FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(MIN_FETCH_INTERVAL_SECONDS)
                        .build()
                )
                config.setDefaultsAsync(RemoteConfigDefaults.values)
            }
        }
    }

    override suspend fun fetchAndActivate(): Result<Unit> {
        val config = remoteConfig
            ?: return Result.failure(IllegalStateException("Firebase app not initialized"))
        return try {
            val task = config.fetchAndActivate()
            suspendCancellableCoroutine { cont ->
                task.addOnSuccessListener {
                    if (cont.isActive) cont.resume(Result.success(Unit))
                }.addOnFailureListener { exception ->
                    if (cont.isActive) cont.resume(Result.failure(exception))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getString(key: String): String {
        val value = remoteConfig?.getString(key)
        return if (!value.isNullOrEmpty()) value else (RemoteConfigDefaults.values[key] as? String ?: "")
    }

    override fun getBoolean(key: String): Boolean {
        return remoteConfig?.getBoolean(key) ?: (RemoteConfigDefaults.values[key] as? Boolean ?: false)
    }

    override fun getLong(key: String): Long {
        return remoteConfig?.getLong(key) ?: ((RemoteConfigDefaults.values[key] as? Number)?.toLong() ?: 0L)
    }

    private companion object {
        const val MIN_FETCH_INTERVAL_SECONDS = 60L
    }
}
