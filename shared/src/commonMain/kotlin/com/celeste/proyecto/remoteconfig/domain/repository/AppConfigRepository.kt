package com.celeste.proyecto.remoteconfig.domain.repository

import com.celeste.proyecto.remoteconfig.domain.model.AppConfig
import kotlinx.coroutines.flow.StateFlow

interface AppConfigRepository {
    val config: StateFlow<AppConfig>
    suspend fun refresh(): Result<AppConfig>
}
