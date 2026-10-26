package com.celeste.proyecto.notes.data.datasource

import com.celeste.proyecto.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryNotesDataSource : NotesRemoteDataSource {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())

    override fun observeNotes(): Flow<Result<List<Note>>> =
        _notes.map { Result.success(it) }

    override suspend fun addNote(text: String): Result<Unit> {
        val newNote = Note(
            id = System.currentTimeMillis().toString(),
            text = text,
            createdAt = System.currentTimeMillis(),
        )
        _notes.value = (_notes.value + newNote).sortedByDescending { it.createdAt }
        return Result.success(Unit)
    }

    override suspend fun deleteNote(id: String): Result<Unit> {
        _notes.value = _notes.value.filterNot { it.id == id }
        return Result.success(Unit)
    }
}
