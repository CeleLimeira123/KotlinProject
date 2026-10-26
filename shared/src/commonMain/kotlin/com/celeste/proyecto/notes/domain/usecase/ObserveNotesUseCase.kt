package com.celeste.proyecto.notes.domain.usecase

import com.celeste.proyecto.notes.domain.model.Note
import com.celeste.proyecto.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class ObserveNotesUseCase(private val repository: NotesRepository) {
    operator fun invoke(): Flow<Result<List<Note>>> = repository.observeNotes()
}
