package com.celeste.proyecto.notes.presentation.state

import com.celeste.proyecto.notes.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteText: String = "",
    val isLoading: Boolean = true,
    val isSubmitting: Boolean = false,
    val error: String? = null,
)
