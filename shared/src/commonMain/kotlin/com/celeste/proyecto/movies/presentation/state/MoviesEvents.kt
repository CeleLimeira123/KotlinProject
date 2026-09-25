package com.celeste.proyecto.movies.presentation.state

sealed interface MoviesEvents {
    data object LoadMovies : MoviesEvents
    data class OnMovieSelected(val movieId: String) : MoviesEvents
}