package com.celeste.proyecto.moviedetail.domain.usecase

import com.celeste.proyecto.moviedetail.domain.model.MovieDetailModel
import com.celeste.proyecto.moviedetail.domain.repository.MovieDetailRepository

class GetMovieDetailUseCase(private val repository: MovieDetailRepository) {
    suspend operator fun invoke(movieId: String): MovieDetailModel {
        return repository.getMovieDetail(movieId)
    }
}