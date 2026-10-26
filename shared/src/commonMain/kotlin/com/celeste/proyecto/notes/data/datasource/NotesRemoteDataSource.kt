package com.celeste.proyecto.notes.data.datasource

import com.celeste.proyecto.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRemoteDataSource {
    fun observeNotes(): Flow<Result<List<Note>>>
    suspend fun addNote(text: String): Result<Unit>
    suspend fun deleteNote(id: String): Result<Unit>
}
