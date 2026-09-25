package com.celeste.proyecto.movies.data.repository

import com.celeste.proyecto.movies.domain.model.MovieModel
import com.celeste.proyecto.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.delay

class MoviesRepositoryImpl : MoviesRepository {
    override suspend fun getMovies(): List<MovieModel> {
        delay(500)
        return listOf(
            MovieModel(id = "1", title = "Película 1", overview = "Descripción de la película 1", posterUrl = ""),
            MovieModel(id = "2", title = "Película 2", overview = "Descripción de la película 2", posterUrl = "")
        )
    }
}