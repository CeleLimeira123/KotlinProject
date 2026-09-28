package com.celeste.proyecto.catalog.domain.repository

import com.celeste.proyecto.catalog.domain.model.MovieModel

interface CatalogRepository {
    suspend fun getMovies(): Result<List<MovieModel>>
}