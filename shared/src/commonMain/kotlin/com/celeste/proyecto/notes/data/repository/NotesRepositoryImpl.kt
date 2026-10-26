package com.celeste.proyecto.notes.data.repository

import com.celeste.proyecto.notes.data.datasource.NotesRemoteDataSource
import com.celeste.proyecto.notes.domain.model.Note
import com.celeste.proyecto.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val remoteDataSource: NotesRemoteDataSource,
) : NotesRepository {
    override fun observeNotes(): Flow<Result<List<Note>>> = remoteDataSource.observeNotes()
    override suspend fun addNote(text: String): Result<Unit> = remoteDataSource.addNote(text)
    override suspend fun deleteNote(id: String): Result<Unit> = remoteDataSource.deleteNote(id)
}
