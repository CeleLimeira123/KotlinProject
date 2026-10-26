package com.celeste.proyecto.signin.data.repository

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.signin.domain.model.SignInModel
import com.celeste.proyecto.signin.domain.repository.SignInRepository

class SignInRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : SignInRepository {
    override suspend fun signIn(email: String, pass: String): SignInModel {
        return authRemoteDataSource.signIn(email, pass).getOrThrow()
    }
}
