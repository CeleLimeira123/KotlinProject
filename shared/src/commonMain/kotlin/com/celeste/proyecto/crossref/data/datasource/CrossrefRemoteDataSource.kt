package com.celeste.proyecto.crossref.data.datasource

import com.celeste.proyecto.crossref.data.dto.CrossrefResponseDto
import com.celeste.proyecto.crossref.data.service.CrossrefService

class CrossrefRemoteDataSource(
    private val service: CrossrefService,
) {
    suspend fun getWorks(query: String, rows: Int): CrossrefResponseDto {
        return service.fetchWorks(query, rows)
    }
}