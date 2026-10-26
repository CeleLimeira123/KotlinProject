package com.celeste.proyecto.profile.data.repository

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.profile.domain.model.ProfileModel
import com.celeste.proyecto.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : ProfileRepository {
    override suspend fun getProfile(): ProfileModel {
        if (!authRemoteDataSource.isUserLoggedIn || authRemoteDataSource.currentUserEmail == null) {
            error("No hay una sesión activa de Firebase Authentication")
        }
        val email = authRemoteDataSource.currentUserEmail.orEmpty()
        val uid = authRemoteDataSource.currentUserId.orEmpty()
        val realDisplayName = authRemoteDataSource.currentUserName
        val name = if (!realDisplayName.isNullOrBlank()) realDisplayName else email.substringBefore('@').replaceFirstChar { it.uppercase() }

        return ProfileModel(
            userId = uid,
            name = name,
            email = email,
            avatarUrl = "",
        )
    }
}
