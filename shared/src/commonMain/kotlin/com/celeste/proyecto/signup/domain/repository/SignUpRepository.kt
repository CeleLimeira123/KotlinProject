package com.celeste.proyecto.signup.domain.repository

import com.celeste.proyecto.signup.domain.model.SignUpModel

interface SignUpRepository {
    suspend fun signUp(email: String, pass: String, name: String): SignUpModel
}