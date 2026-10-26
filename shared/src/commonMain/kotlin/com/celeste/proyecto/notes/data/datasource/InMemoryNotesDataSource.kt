package com.celeste.proyecto.notes.data.datasource

import com.celeste.proyecto.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryNotesDataSource : NotesRemoteDataSource {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    private var counter = 1000L

    override fun observeNotes(): Flow<Result<List<Note>>> =
        _notes.map { Result.success(it) }

    override suspend fun addNote(text: String): Result<Unit> {
        counter++
        val newNote = Note(
            id = counter.toString(),
            text = text,
            createdAt = counter,
        )
        _notes.value = (_notes.value + newNote).sortedByDescending { it.createdAt }
        return Result.success(Unit)
    }

    override suspend fun deleteNote(id: String): Result<Unit> {
        _notes.value = _notes.value.filterNot { it.id == id }
        return Result.success(Unit)
    }
}
