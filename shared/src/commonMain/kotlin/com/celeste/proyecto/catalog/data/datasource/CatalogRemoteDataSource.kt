package com.celeste.proyecto.catalog.data.datasource

import com.celeste.proyecto.catalog.data.dto.CatalogDto
import com.celeste.proyecto.catalog.data.service.CatalogService

class CatalogRemoteDataSource(
    private val service: CatalogService,
) {
    suspend fun getCatalog(): CatalogDto {
        return service.fetchCatalog()
    }
}