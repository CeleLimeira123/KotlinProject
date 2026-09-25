package com.celeste.proyecto.moviedetail.presentation.effects

sealed interface MovieDetailEffects {
    data object NavigateBack : MovieDetailEffects
}