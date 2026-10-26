package com.celeste.proyecto.core.storage

import android.content.Context
import org.koin.java.KoinJavaComponent.getKoin

class AndroidSettingsStorage(private val context: Context) : SettingsStorage {
    private val prefs = context.getSharedPreferences("celeste_prefs", Context.MODE_PRIVATE)

    override fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}

actual fun createSettingsStorage(): SettingsStorage {
    val context: Context = getKoin().get()
    return AndroidSettingsStorage(context)
}
