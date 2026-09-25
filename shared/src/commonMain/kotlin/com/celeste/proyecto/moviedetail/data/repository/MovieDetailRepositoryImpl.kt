package com.celeste.proyecto.moviedetail.data.repository

import com.celeste.proyecto.moviedetail.domain.model.MovieDetailModel
import com.celeste.proyecto.moviedetail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.delay

class MovieDetailRepositoryImpl : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: String): MovieDetailModel {
        delay(500)
        return MovieDetailModel(
            id = movieId,
            title = "Película $movieId",
            overview = "Detalle completo de la película $movieId",
            rating = 8.5,
            releaseDate = "2024-01-01",
        )
    }
}