package com.celeste.proyecto

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.celeste.proyecto.core.navigation.AppNavHost
import com.celeste.proyecto.di.appModules
import org.koin.compose.KoinApplication

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

    KoinApplication(application = {
        modules(appModules)
    }) {
        MaterialTheme {
            AppNavHost()
        }
    }
}