package com.celeste.proyecto.notes.presentation.state

sealed interface NotesEvents {
    data class OnTextChanged(val text: String) : NotesEvents
    data object OnAddNoteClicked : NotesEvents
    data class OnDeleteNoteClicked(val id: String) : NotesEvents
}
