package com.celeste.proyecto.auth.data.datasource

import android.content.Context
import android.util.Log
import com.celeste.proyecto.signin.domain.model.SignInModel
import com.celeste.proyecto.signup.domain.model.SignUpModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseAuthDataSource(
    private val context: Context,
) : AuthRemoteDataSource {

    private val auth: FirebaseAuth? by lazy {
        if (FirebaseApp.getApps(context).isEmpty()) {
            null
        } else {
            FirebaseAuth.getInstance()
        }
    }

    override val currentUserId: String?
        get() = auth?.currentUser?.uid

    override val currentUserEmail: String?
        get() = auth?.currentUser?.email

    override val currentUserName: String?
        get() = auth?.currentUser?.displayName

    override val isUserLoggedIn: Boolean
        get() = auth?.currentUser != null

    override suspend fun signIn(email: String, pass: String): Result<SignInModel> {
        val firebaseAuth = auth
            ?: return Result.failure(IllegalStateException("Firebase App not initialized"))
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, pass).awaitTask()
            val user = authResult.user
            val uid = user?.uid ?: ""
            val userEmail = user?.email ?: email

            val tokenString = try {
                user?.getIdToken(false)?.awaitTask()?.token ?: uid
            } catch (_: Exception) {
                uid
            }

            Result.success(SignInModel(token = tokenString, email = userEmail))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, pass: String, name: String): Result<SignUpModel> {
        val firebaseAuth = auth
            ?: return Result.failure(IllegalStateException("Firebase App not initialized"))
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, pass).awaitTask()
            val user = authResult.user
            val uid = user?.uid ?: ""
            val userEmail = user?.email ?: email

            if (user != null && name.isNotBlank()) {
                try {
                    user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build()).awaitTask()
                } catch (profileError: Exception) {
                    Log.w("FirebaseAuth", "Cuenta creada exitosamente en Firebase Auth, pero falló la actualización del nombre de perfil: ${profileError.message}")
                }
            }

            Result.success(SignUpModel(userId = uid, email = userEmail))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        auth?.signOut()
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
}
