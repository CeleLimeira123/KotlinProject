package com.celeste.proyecto.movies.domain.usecase

import com.celeste.proyecto.movies.domain.model.MovieModel
import com.celeste.proyecto.movies.domain.repository.MoviesRepository

class GetMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(): List<MovieModel> {
        return repository.getMovies()
    }
}