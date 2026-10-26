package com.celeste.proyecto.core.localization

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

actual object LocalAppLocale {
    private var systemLocale: Locale? = null

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current
        val defaultLocale = systemLocale ?: Locale.getDefault().also { systemLocale = it }
        val targetLocale = if (value.isNullOrEmpty()) defaultLocale else Locale.forLanguageTag(value)
        Locale.setDefault(targetLocale)
        val newConfiguration = Configuration(configuration).apply { setLocale(targetLocale) }
        return LocalConfiguration.provides(newConfiguration)
    }
}
