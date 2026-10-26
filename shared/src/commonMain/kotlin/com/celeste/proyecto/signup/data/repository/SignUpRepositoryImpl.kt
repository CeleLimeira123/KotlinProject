package com.celeste.proyecto.signup.data.repository

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.signup.domain.model.SignUpModel
import com.celeste.proyecto.signup.domain.repository.SignUpRepository

class SignUpRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : SignUpRepository {
    override suspend fun signUp(email: String, pass: String, name: String): SignUpModel {
        return authRemoteDataSource.signUp(email, pass, name).getOrThrow()
    }
}
