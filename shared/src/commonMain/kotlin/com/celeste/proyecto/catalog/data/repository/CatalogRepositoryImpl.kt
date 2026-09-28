package com.celeste.proyecto.catalog.data.repository

import com.celeste.proyecto.catalog.data.datasource.CatalogRemoteDataSource
import com.celeste.proyecto.catalog.data.mapper.toDomainList
import com.celeste.proyecto.catalog.domain.model.MovieModel
import com.celeste.proyecto.catalog.domain.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val remoteDataSource: CatalogRemoteDataSource,
) : CatalogRepository {

    override suspend fun getMovies(): Result<List<MovieModel>> {
        return try {
            val catalogDto = remoteDataSource.getCatalog()
            Result.success(catalogDto.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}