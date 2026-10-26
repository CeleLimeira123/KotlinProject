package com.celeste.proyecto

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.celeste.proyecto.core.localization.LanguageManager
import com.celeste.proyecto.core.localization.LocalAppLocale
import com.celeste.proyecto.core.navigation.AppNavHost
import com.celeste.proyecto.di.appModules
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.mp.KoinPlatformTools

@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .build()
    }

    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        KoinApplication(application = {
            modules(appModules)
        }) {
            AppContent()
        }
    } else {
        KoinContext {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
    val languageManager: LanguageManager = koinInject()
    val selectedLanguage by languageManager.currentLanguage.collectAsState()

    CompositionLocalProvider(LocalAppLocale provides selectedLanguage) {
        MaterialTheme {
            AppNavHost()
        }
    }
}
