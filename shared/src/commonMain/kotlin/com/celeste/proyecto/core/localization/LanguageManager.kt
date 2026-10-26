package com.celeste.proyecto.core.localization

import com.celeste.proyecto.core.storage.SettingsStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageManager(
    private val settingsStorage: SettingsStorage,
) {
    private val _currentLanguage = MutableStateFlow(
        settingsStorage.getString(KEY_LANGUAGE, "es"),
    )
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(languageCode: String) {
        if (languageCode == "es" || languageCode == "en") {
            _currentLanguage.value = languageCode
            settingsStorage.putString(KEY_LANGUAGE, languageCode)
        }
    }

    private companion object {
        const val KEY_LANGUAGE = "app_language"
    }
}
