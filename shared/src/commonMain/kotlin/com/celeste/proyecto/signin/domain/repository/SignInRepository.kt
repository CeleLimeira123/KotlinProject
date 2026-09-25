package com.celeste.proyecto.signin.domain.repository

import com.celeste.proyecto.signin.domain.model.SignInModel

interface SignInRepository {
    suspend fun signIn(email: String, pass: String): SignInModel
}