package com.celeste.proyecto.crossref.data.repository

import com.celeste.proyecto.crossref.data.datasource.CrossrefRemoteDataSource
import com.celeste.proyecto.crossref.data.mapper.toDomainList
import com.celeste.proyecto.crossref.domain.model.CrossrefArticleModel
import com.celeste.proyecto.crossref.domain.repository.CrossrefRepository

class CrossrefRepositoryImpl(
    private val remoteDataSource: CrossrefRemoteDataSource,
) : CrossrefRepository {

    override suspend fun searchWorks(query: String, rows: Int): Result<List<CrossrefArticleModel>> {
        return try {
            val dto = remoteDataSource.getWorks(query, rows)
            val domainList = dto.toDomainList()
            if (domainList.isEmpty()) {
                Result.failure(Exception("No se encontraron artículos para '$query'"))
            } else {
                Result.success(domainList)
            }
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Error al conectar con la API de Crossref"))
        }
    }
}