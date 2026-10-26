package com.celeste.proyecto.auth.data.datasource

import com.celeste.proyecto.signin.domain.model.SignInModel
import com.celeste.proyecto.signup.domain.model.SignUpModel

class InMemoryAuthDataSource : AuthRemoteDataSource {
    override val currentUserId: String? get() = null
    override val currentUserEmail: String? get() = null
    override val currentUserName: String? get() = null
    override val isUserLoggedIn: Boolean get() = false

    override suspend fun signIn(email: String, pass: String): Result<SignInModel> {
        return Result.failure(UnsupportedOperationException("Firebase Authentication no está disponible en esta plataforma"))
    }

    override suspend fun signUp(email: String, pass: String, name: String): Result<SignUpModel> {
        return Result.failure(UnsupportedOperationException("Firebase Authentication no está disponible en esta plataforma"))
    }

    override fun signOut() {
        // No-op
    }
}
