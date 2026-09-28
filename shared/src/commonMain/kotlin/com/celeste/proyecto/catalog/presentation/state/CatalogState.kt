package com.celeste.proyecto.catalog.presentation.state

import com.celeste.proyecto.catalog.domain.model.MovieModel

sealed interface CatalogState {
    object Loading : CatalogState
    data class Success(val movies: List<MovieModel>) : CatalogState
    data class Error(val message: String) : CatalogState
}