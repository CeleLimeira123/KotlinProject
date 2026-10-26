package com.celeste.proyecto.core.storage

interface SettingsStorage {
    fun getString(key: String, defaultValue: String): String
    fun putString(key: String, value: String)
}

expect fun createSettingsStorage(): SettingsStorage
