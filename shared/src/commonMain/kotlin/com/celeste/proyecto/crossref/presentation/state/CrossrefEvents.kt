package com.celeste.proyecto.crossref.presentation.state

sealed interface CrossrefEvents {
    data class OnQueryChanged(val query: String) : CrossrefEvents
    data class OnRowsChanged(val rows: Int) : CrossrefEvents
    data object OnSearchClicked : CrossrefEvents
    data object OnDismissError : CrossrefEvents
}