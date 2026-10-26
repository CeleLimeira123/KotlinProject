package com.celeste.proyecto

import com.celeste.proyecto.core.localization.LanguageManager
import com.celeste.proyecto.core.storage.SettingsStorage
import com.celeste.proyecto.core.theme.FontSizeScale
import com.celeste.proyecto.core.theme.ThemeManager
import com.celeste.proyecto.core.theme.ThemeMode
import com.celeste.proyecto.notes.data.datasource.InMemoryNotesDataSource
import com.celeste.proyecto.notes.data.repository.NotesRepositoryImpl
import com.celeste.proyecto.notes.domain.usecase.AddNoteUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FakeSettingsStorage : SettingsStorage {
    private val map = mutableMapOf<String, String>()

    override fun getString(key: String, defaultValue: String): String {
        return map[key] ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        map[key] = value
    }
}

class AppUnitTests {

    @Test
    fun testLanguageManagerSwitching() {
        val storage = FakeSettingsStorage()
        val languageManager = LanguageManager(storage)

        assertEquals("es", languageManager.currentLanguage.value)

        languageManager.setLanguage("en")
        assertEquals("en", languageManager.currentLanguage.value)
        assertEquals("en", storage.getString("app_language", "es"))
    }

    @Test
    fun testThemeManagerModesAndFontScaling() {
        val storage = FakeSettingsStorage()
        val themeManager = ThemeManager(storage)

        assertEquals(ThemeMode.SYSTEM, themeManager.themeMode.value)
        assertEquals(FontSizeScale.NORMAL, themeManager.fontSizeScale.value)

        themeManager.setThemeMode(ThemeMode.DARK)
        assertEquals(ThemeMode.DARK, themeManager.themeMode.value)

        themeManager.setFontSizeScale(FontSizeScale.LARGE)
        assertEquals(FontSizeScale.LARGE, themeManager.fontSizeScale.value)
    }

    @Test
    fun testAddNoteUseCaseValidation() = runBlocking {
        val dataSource = InMemoryNotesDataSource()
        val repository = NotesRepositoryImpl(dataSource)
        val addNoteUseCase = AddNoteUseCase(repository)

        val blankResult = addNoteUseCase("   ")
        assertTrue(blankResult.isFailure)

        val validResult = addNoteUseCase("Nota de prueba unitaria")
        assertTrue(validResult.isSuccess)
    }
}
