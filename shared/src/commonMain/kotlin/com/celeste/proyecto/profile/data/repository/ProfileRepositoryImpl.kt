package com.celeste.proyecto.profile.data.repository

import com.celeste.proyecto.profile.domain.model.ProfileModel
import com.celeste.proyecto.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.delay

class ProfileRepositoryImpl : ProfileRepository {
    override suspend fun getProfile(): ProfileModel {
        delay(500)
        return ProfileModel(
            userId = "123",
            name = "Celeste",
            email = "celeste@example.com",
            avatarUrl = "https://via.placeholder.com/150",
        )
    }
}