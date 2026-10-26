package com.celeste.proyecto.notes.data.datasource

import android.content.Context
import com.celeste.proyecto.notes.domain.model.Note
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseNotesDataSource(
    private val context: Context,
) : NotesRemoteDataSource {

    private fun userNotesReference(): Result<DatabaseReference> {
        if (FirebaseApp.getApps(context).isEmpty()) {
            return Result.failure(IllegalStateException("Firebase App not initialized"))
        }
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.failure(IllegalStateException("Usuario no autenticado en Firebase"))
        return try {
            Result.success(FirebaseDatabase.getInstance().getReference(NOTES_PATH).child(uid))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeNotes(): Flow<Result<List<Note>>> = callbackFlow {
        val reference = userNotesReference().getOrElse { error ->
            trySend(Result.failure(error))
            awaitClose()
            return@callbackFlow
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notes = snapshot.children.mapNotNull { child ->
                    val text = child.child("text").getValue(String::class.java) ?: return@mapNotNull null
                    val createdAt = child.child("createdAt").getValue(Long::class.java) ?: 0L
                    Note(id = child.key.orEmpty(), text = text, createdAt = createdAt)
                }.sortedByDescending { it.createdAt }
                trySend(Result.success(notes))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(error.toException()))
            }
        }

        reference.addValueEventListener(listener)
        awaitClose { reference.removeEventListener(listener) }
    }

    override suspend fun addNote(text: String): Result<Unit> = try {
        val reference = userNotesReference().getOrThrow()
        val key = reference.push().key ?: error("No se pudo generar id para la nota")
        reference.child(key).setValue(mapOf("text" to text, "createdAt" to System.currentTimeMillis())).awaitTask()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteNote(id: String): Result<Unit> = try {
        userNotesReference().getOrThrow().child(id).removeValue().awaitTask()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private suspend fun <T> Task<T>.awaitTask(): T =
        suspendCancellableCoroutine { cont ->
            addOnSuccessListener { result ->
                if (cont.isActive) cont.resume(result)
            }
            addOnFailureListener { exception ->
                if (cont.isActive) cont.resumeWithException(exception)
            }
        }

    private companion object {
        const val NOTES_PATH = "notes"
    }
}
