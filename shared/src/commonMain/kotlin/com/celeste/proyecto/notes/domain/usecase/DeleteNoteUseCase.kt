package com.celeste.proyecto.notes.domain.usecase

import com.celeste.proyecto.notes.domain.repository.NotesRepository

class DeleteNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.deleteNote(id)
}
