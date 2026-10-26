package com.celeste.proyecto.core.theme

import com.celeste.proyecto.core.storage.SettingsStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeManager(
    private val settingsStorage: SettingsStorage,
) {
    private val _themeMode = MutableStateFlow(
        try {
            ThemeMode.valueOf(settingsStorage.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name))
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        },
    )
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _fontSizeScale = MutableStateFlow(
        try {
            FontSizeScale.valueOf(settingsStorage.getString(KEY_FONT_SIZE_SCALE, FontSizeScale.NORMAL.name))
        } catch (_: Exception) {
            FontSizeScale.NORMAL
        },
    )
    val fontSizeScale: StateFlow<FontSizeScale> = _fontSizeScale.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        settingsStorage.putString(KEY_THEME_MODE, mode.name)
    }

    fun setFontSizeScale(scale: FontSizeScale) {
        _fontSizeScale.value = scale
        settingsStorage.putString(KEY_FONT_SIZE_SCALE, scale.name)
    }

    private companion object {
        const val KEY_THEME_MODE = "app_theme_mode"
        const val KEY_FONT_SIZE_SCALE = "app_font_size_scale"
    }
}
