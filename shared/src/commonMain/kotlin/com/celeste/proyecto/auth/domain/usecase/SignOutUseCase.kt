package com.celeste.proyecto.auth.domain.usecase

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource

class SignOutUseCase(
    private val authRemoteDataSource: AuthRemoteDataSource,
) {
    operator fun invoke() {
        authRemoteDataSource.signOut()
    }
}
