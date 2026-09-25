package com.celeste.proyecto.moviedetail.presentation.state

import com.celeste.proyecto.moviedetail.domain.model.MovieDetailModel

data class MovieDetailState(
    val movieDetail: MovieDetailModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)