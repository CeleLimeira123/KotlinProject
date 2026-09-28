package com.celeste.proyecto.catalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.catalog.domain.usecase.GetCatalogMoviesUseCase
import com.celeste.proyecto.catalog.presentation.state.CatalogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val getCatalogMoviesUseCase: GetCatalogMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val uiState: StateFlow<CatalogState> = _uiState.asStateFlow()

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        viewModelScope.launch {
            _uiState.value = CatalogState.Loading
            val result = getCatalogMoviesUseCase()
            result.fold(
                onSuccess = { movies ->
                    _uiState.value = CatalogState.Success(movies)
                },
                onFailure = { error ->
                    _uiState.value = CatalogState.Error(error.message ?: "Error desconocido")
                }
            )
        }
    }
}