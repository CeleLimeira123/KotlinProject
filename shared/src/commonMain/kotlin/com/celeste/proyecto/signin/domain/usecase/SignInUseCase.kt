package com.celeste.proyecto.signin.domain.usecase

import com.celeste.proyecto.signin.domain.model.SignInModel
import com.celeste.proyecto.signin.domain.repository.SignInRepository
import com.celeste.proyecto.signin.domain.vo.Email
import com.celeste.proyecto.signin.domain.vo.Password

class SignInUseCase(private val repository: SignInRepository) {
    suspend operator fun invoke(email: Email, pass: Password): Result<SignInModel> {
        if (!email.isValid()) {
            return Result.failure(IllegalArgumentException("Correo inválido"))
        }
        if (!pass.isValid()) {
            return Result.failure(IllegalArgumentException("La contraseña debe tener al menos 6 caracteres"))
        }
        return try {
            Result.success(repository.signIn(email.value, pass.value))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}