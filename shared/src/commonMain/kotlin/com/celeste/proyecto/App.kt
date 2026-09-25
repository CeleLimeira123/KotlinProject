package com.celeste.proyecto

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.celeste.proyecto.core.navigation.AppNavHost
import com.celeste.proyecto.di.appModules
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModules)
    }) {
        MaterialTheme {
            AppNavHost()
        }
    }
}