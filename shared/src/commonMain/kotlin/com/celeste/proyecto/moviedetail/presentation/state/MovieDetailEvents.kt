package com.celeste.proyecto.moviedetail.presentation.state

sealed interface MovieDetailEvents {
    data class LoadMovieDetail(val movieId: String) : MovieDetailEvents
    data object OnBackClicked : MovieDetailEvents
}