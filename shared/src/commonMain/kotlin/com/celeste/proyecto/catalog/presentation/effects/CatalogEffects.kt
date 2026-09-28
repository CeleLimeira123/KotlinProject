package com.celeste.proyecto.catalog.presentation.effects

sealed interface CatalogEffects {
    data class NavigateToDetail(val movieId: String) : CatalogEffects
}