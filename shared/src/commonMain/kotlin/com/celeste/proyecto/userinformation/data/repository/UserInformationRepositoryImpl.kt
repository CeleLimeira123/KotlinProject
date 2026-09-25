package com.celeste.proyecto.userinformation.data.repository

import com.celeste.proyecto.userinformation.domain.model.UserInformationModel
import com.celeste.proyecto.userinformation.domain.repository.UserInformationRepository
import kotlinx.coroutines.delay

class UserInformationRepositoryImpl : UserInformationRepository {
    override suspend fun getUserInformation(): UserInformationModel {
        delay(500)
        return UserInformationModel(
            address = "Av. Principal 123",
            phone = "+591 70000000",
            birthDate = "1995-05-15",
        )
    }
}