package com.celeste.proyecto.catalog.domain.usecase

import com.celeste.proyecto.catalog.domain.model.MovieModel
import com.celeste.proyecto.catalog.domain.repository.CatalogRepository

class GetCatalogMoviesUseCase(private val repository: CatalogRepository) {
    suspend operator fun invoke(): Result<List<MovieModel>> {
        return repository.getMovies()
    }
}