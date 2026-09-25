package com.celeste.proyecto.profile.domain.usecase

import com.celeste.proyecto.profile.domain.model.ProfileModel
import com.celeste.proyecto.profile.domain.repository.ProfileRepository

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(): ProfileModel {
        return repository.getProfile()
    }
}