package com.celeste.proyecto.remoteconfig.data.datasource

import com.celeste.proyecto.remoteconfig.domain.model.AppConfig

object RemoteConfigKeys {
    const val MENSAJE_BIENVENIDA = "mensaje_bienvenida"
    const val MOSTRAR_PROMO = "mostrar_promo"
    const val MAX_PELICULAS_INICIO = "max_peliculas_inicio"
    const val CATEGORIA_DESTACADA = "categoria_destacada"
}

object RemoteConfigDefaults {
    val values: Map<String, Any> = mapOf(
        RemoteConfigKeys.MENSAJE_BIENVENIDA to AppConfig.DEFAULT.mensajeBienvenida,
        RemoteConfigKeys.MOSTRAR_PROMO to AppConfig.DEFAULT.mostrarPromo,
        RemoteConfigKeys.MAX_PELICULAS_INICIO to AppConfig.DEFAULT.maxPeliculasInicio,
        RemoteConfigKeys.CATEGORIA_DESTACADA to AppConfig.DEFAULT.categoriaDestacada,
    )
}
