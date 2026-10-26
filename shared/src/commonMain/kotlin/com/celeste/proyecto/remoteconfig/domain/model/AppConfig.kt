package com.celeste.proyecto.remoteconfig.domain.model

data class AppConfig(
    val mensajeBienvenida: String = "¡Bienvenido a Celeste!",
    val mostrarPromo: Boolean = false,
    val maxSugerenciasHome: Int = 5,
    val categoriaDestacada: String = "bienestar",
) {
    companion object {
        val DEFAULT = AppConfig()
    }
}
