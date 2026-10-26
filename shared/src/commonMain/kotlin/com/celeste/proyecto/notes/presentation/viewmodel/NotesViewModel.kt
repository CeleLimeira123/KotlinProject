package com.celeste.proyecto.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.notes.domain.usecase.AddNoteUseCase
import com.celeste.proyecto.notes.domain.usecase.DeleteNoteUseCase
import com.celeste.proyecto.notes.domain.usecase.ObserveNotesUseCase
import com.celeste.proyecto.notes.presentation.state.NotesEvents
import com.celeste.proyecto.notes.presentation.state.NotesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesState())
    val uiState: StateFlow<NotesState> = _uiState.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            observeNotesUseCase().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        _uiState.value = _uiState.value.copy(
                            notes = list,
                            isLoading = false,
                            error = null,
                        )
                    },
                    onFailure = { err ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = err.message ?: "Error al cargar notas",
                        )
                    },
                )
            }
        }
    }

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.OnTextChanged -> {
                _uiState.value = _uiState.value.copy(noteText = event.text)
            }
            is NotesEvents.OnAddNoteClicked -> {
                val text = _uiState.value.noteText
                if (text.isBlank()) return
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(isSubmitting = true)
                    val result = addNoteUseCase(text)
                    result.fold(
                        onSuccess = {
                            _uiState.value = _uiState.value.copy(noteText = "", isSubmitting = false)
                        },
                        onFailure = { err ->
                            _uiState.value = _uiState.value.copy(
                                isSubmitting = false,
                                error = err.message ?: "Error al agregar nota",
                            )
                        },
                    )
                }
            }
            is NotesEvents.OnDeleteNoteClicked -> {
                viewModelScope.launch {
                    deleteNoteUseCase(event.id)
                }
            }
        }
    }
}
