package com.celeste.proyecto.remoteconfig.data.repository

import com.celeste.proyecto.remoteconfig.data.datasource.RemoteConfigDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.RemoteConfigKeys
import com.celeste.proyecto.remoteconfig.domain.model.AppConfig
import com.celeste.proyecto.remoteconfig.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppConfigRepositoryImpl(
    private val dataSource: RemoteConfigDataSource,
) : AppConfigRepository {

    private val _config = MutableStateFlow(read())
    override val config: StateFlow<AppConfig> = _config.asStateFlow()

    override suspend fun refresh(): Result<AppConfig> =
        dataSource.fetchAndActivate().map {
            read().also { fresh -> _config.value = fresh }
        }

    private fun read(): AppConfig {
        val mensaje = dataSource.getString(RemoteConfigKeys.MENSAJE_BIENVENIDA).ifBlank { AppConfig.DEFAULT.mensajeBienvenida }
        val promo = dataSource.getBoolean(RemoteConfigKeys.MOSTRAR_PROMO)
        val maxPeliculas = dataSource.getLong(RemoteConfigKeys.MAX_PELICULAS_INICIO).toInt().let {
            if (it <= 0) AppConfig.DEFAULT.maxPeliculasInicio else it
        }
        val categoria = dataSource.getString(RemoteConfigKeys.CATEGORIA_DESTACADA).ifBlank { AppConfig.DEFAULT.categoriaDestacada }

        return AppConfig(
            mensajeBienvenida = mensaje,
            mostrarPromo = promo,
            maxPeliculasInicio = maxPeliculas,
            categoriaDestacada = categoria,
        )
    }
}
