package com.celeste.proyecto.userinformation.domain.usecase

import com.celeste.proyecto.userinformation.domain.model.UserInformationModel
import com.celeste.proyecto.userinformation.domain.repository.UserInformationRepository

class GetUserInformationUseCase(private val repository: UserInformationRepository) {
    suspend operator fun invoke(): UserInformationModel {
        return repository.getUserInformation()
    }
}