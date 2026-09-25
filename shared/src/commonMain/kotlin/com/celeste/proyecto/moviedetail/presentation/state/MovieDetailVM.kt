package com.celeste.proyecto.moviedetail.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.moviedetail.domain.usecase.GetMovieDetailUseCase
import com.celeste.proyecto.moviedetail.presentation.effects.MovieDetailEffects
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailVM(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState: StateFlow<MovieDetailState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MovieDetailEffects>()
    val effect: SharedFlow<MovieDetailEffects> = _effect.asSharedFlow()

    fun onEvent(event: MovieDetailEvents) {
        when (event) {
            is MovieDetailEvents.LoadMovieDetail -> loadMovieDetail(event.movieId)
            is MovieDetailEvents.OnBackClicked -> {
                viewModelScope.launch {
                    _effect.emit(MovieDetailEffects.NavigateBack)
                }
            }
        }
    }

    private fun loadMovieDetail(movieId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val detail = getMovieDetailUseCase(movieId)
                _uiState.update { it.copy(movieDetail = detail, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al cargar detalle") }
            }
        }
    }
}