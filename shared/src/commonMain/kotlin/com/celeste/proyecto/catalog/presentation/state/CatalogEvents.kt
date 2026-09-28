package com.celeste.proyecto.catalog.presentation.state

sealed interface CatalogEvents {
    data object LoadCatalog : CatalogEvents
    data class OnMovieClicked(val movieId: String) : CatalogEvents
}