package com.celeste.proyecto.userinformation.domain.repository

import com.celeste.proyecto.userinformation.domain.model.UserInformationModel

interface UserInformationRepository {
    suspend fun getUserInformation(): UserInformationModel
}