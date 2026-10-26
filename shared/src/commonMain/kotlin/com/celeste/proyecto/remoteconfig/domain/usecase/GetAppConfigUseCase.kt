package com.celeste.proyecto.remoteconfig.domain.usecase

import com.celeste.proyecto.remoteconfig.domain.model.AppConfig
import com.celeste.proyecto.remoteconfig.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.StateFlow

class GetAppConfigUseCase(
    private val repository: AppConfigRepository,
) {
    val config: StateFlow<AppConfig> = repository.config

    suspend fun refresh(): Result<AppConfig> {
        return repository.refresh()
    }
}
