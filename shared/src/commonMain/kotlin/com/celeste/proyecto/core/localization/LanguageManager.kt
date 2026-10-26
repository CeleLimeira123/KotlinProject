package com.celeste.proyecto.core.localization

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageManager {
    private val _currentLanguage = MutableStateFlow("es")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(languageCode: String) {
        if (languageCode == "es" || languageCode == "en") {
            _currentLanguage.value = languageCode
        }
    }
}
