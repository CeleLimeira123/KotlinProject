package com.celeste.proyecto.userinformation.presentation.effects

sealed interface UserInformationEffects {
    data class ShowToast(val message: String) : UserInformationEffects
    data object NavigateBack : UserInformationEffects
}