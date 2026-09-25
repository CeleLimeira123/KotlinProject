package com.celeste.proyecto.profile.domain.repository

import com.celeste.proyecto.profile.domain.model.ProfileModel

interface ProfileRepository {
    suspend fun getProfile(): ProfileModel
}