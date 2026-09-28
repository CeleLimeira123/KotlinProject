package com.celeste.proyecto.crossref.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.crossref.domain.usecase.GetCrossrefWorksUseCase
import com.celeste.proyecto.crossref.presentation.effects.CrossrefEffects
import com.celeste.proyecto.crossref.presentation.state.CrossrefEvents
import com.celeste.proyecto.crossref.presentation.state.CrossrefState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrossrefViewModel(
    private val getCrossrefWorksUseCase: GetCrossrefWorksUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrossrefState())
    val uiState: StateFlow<CrossrefState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<CrossrefEffects>()
    val effect: SharedFlow<CrossrefEffects> = _effect.asSharedFlow()

    init {
        search()
    }

    fun onEvent(event: CrossrefEvents) {
        when (event) {
            is CrossrefEvents.OnQueryChanged -> _uiState.update { it.copy(searchQuery = event.query) }
            is CrossrefEvents.OnRowsChanged -> _uiState.update { it.copy(rows = event.rows) }
            is CrossrefEvents.OnSearchClicked -> search()
            is CrossrefEvents.OnDismissError -> _uiState.update { it.copy(error = null) }
        }
    }

    fun search() {
        val query = _uiState.value.searchQuery.trim().ifBlank { "machine learning" }
        val rows = _uiState.value.rows

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getCrossrefWorksUseCase(query, rows).fold(
                onSuccess = { list ->
                    _uiState.update { it.copy(articles = list, isLoading = false, error = null) }
                },
                onFailure = { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            articles = emptyList(),
                            error = throwable.message ?: "Error al consultar Crossref",
                        )
                    }
                },
            )
        }
    }
}