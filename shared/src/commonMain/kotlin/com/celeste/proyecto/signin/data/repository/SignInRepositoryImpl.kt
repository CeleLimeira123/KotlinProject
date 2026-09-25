package com.celeste.proyecto.signin.data.repository

import com.celeste.proyecto.signin.domain.model.SignInModel
import com.celeste.proyecto.signin.domain.repository.SignInRepository
import kotlinx.coroutines.delay

class SignInRepositoryImpl : SignInRepository {
    override suspend fun signIn(email: String, pass: String): SignInModel {
        delay(500) // Simula la llamada de red
        return SignInModel(token = "fake_token_123", email = email)
    }
}