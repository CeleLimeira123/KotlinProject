package com.celeste.proyecto.signup.data.repository

import com.celeste.proyecto.signup.domain.model.SignUpModel
import com.celeste.proyecto.signup.domain.repository.SignUpRepository
import kotlinx.coroutines.delay

class SignUpRepositoryImpl : SignUpRepository {
    override suspend fun signUp(email: String, pass: String, name: String): SignUpModel {
        delay(500)
        return SignUpModel(userId = "user_123", email = email)
    }
}