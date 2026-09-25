package com.celeste.proyecto.movies.presentation.state

import com.celeste.proyecto.movies.domain.model.MovieModel

data class MoviesState(
    val movies: List<MovieModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)