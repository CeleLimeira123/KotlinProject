package com.celeste.proyecto.movies.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.movies.domain.usecase.GetMoviesUseCase
import com.celeste.proyecto.movies.presentation.effects.MoviesEffects
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesVM(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesState())
    val uiState: StateFlow<MoviesState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MoviesEffects>()
    val effect: SharedFlow<MoviesEffects> = _effect.asSharedFlow()

    init {
        loadMovies()
    }

    fun onEvent(event: MoviesEvents) {
        when (event) {
            is MoviesEvents.LoadMovies -> loadMovies()
            is MoviesEvents.OnMovieSelected -> {
                viewModelScope.launch {
                    _effect.emit(MoviesEffects.NavigateToDetail(event.movieId))
                }
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val list = getMoviesUseCase()
                _uiState.update { it.copy(movies = list, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al cargar películas") }
            }
        }
    }
}