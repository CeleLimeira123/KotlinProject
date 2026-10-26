package com.celeste.proyecto.core.storage

import platform.Foundation.NSUserDefaults

class IosSettingsStorage : SettingsStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun getString(key: String, defaultValue: String): String {
        return defaults.stringForKey(key) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        defaults.setObject(value, forKey = key)
    }
}

actual fun createSettingsStorage(): SettingsStorage = IosSettingsStorage()
