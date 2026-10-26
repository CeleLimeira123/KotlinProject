package com.celeste.proyecto.notes.domain.usecase

import com.celeste.proyecto.notes.domain.repository.NotesRepository

class AddNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(text: String): Result<Unit> {
        if (text.isBlank()) return Result.failure(IllegalArgumentException("El texto no puede estar vacío"))
        return repository.addNote(text.trim())
    }
}
