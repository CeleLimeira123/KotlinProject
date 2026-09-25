package com.celeste.proyecto.signup.domain.usecase

import com.celeste.proyecto.signup.domain.model.SignUpModel
import com.celeste.proyecto.signup.domain.repository.SignUpRepository

class SignUpUseCase(private val repository: SignUpRepository) {
    suspend operator fun invoke(email: String, pass: String, name: String): SignUpModel {
        return repository.signUp(email, pass, name)
    }
}