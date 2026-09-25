package com.celeste.proyecto.movies.domain.repository

import com.celeste.proyecto.movies.domain.model.MovieModel

interface MoviesRepository {
    suspend fun getMovies(): List<MovieModel>
}