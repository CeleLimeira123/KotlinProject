package com.celeste.proyecto.moviedetail.domain.repository

import com.celeste.proyecto.moviedetail.domain.model.MovieDetailModel

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: String): MovieDetailModel
}