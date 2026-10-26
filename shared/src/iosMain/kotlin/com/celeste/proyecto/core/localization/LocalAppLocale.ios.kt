package com.celeste.proyecto.core.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalIosLanguage = staticCompositionLocalOf { "es" }

actual object LocalAppLocale {
    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        return LocalIosLanguage.provides(value ?: "es")
    }
}
