package com.celeste.proyecto.auth.data.datasource

import com.celeste.proyecto.signin.domain.model.SignInModel
import com.celeste.proyecto.signup.domain.model.SignUpModel

interface AuthRemoteDataSource {
    val currentUserId: String?
    val currentUserEmail: String?
    val currentUserName: String?
    val isUserLoggedIn: Boolean
    suspend fun signIn(email: String, pass: String): Result<SignInModel>
    suspend fun signUp(email: String, pass: String, name: String): Result<SignUpModel>
    fun signOut()
}
