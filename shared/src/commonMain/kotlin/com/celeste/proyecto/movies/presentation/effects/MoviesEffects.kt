package com.celeste.proyecto.movies.presentation.effects

sealed interface MoviesEffects {
    data class NavigateToDetail(val movieId: String) : MoviesEffects
}