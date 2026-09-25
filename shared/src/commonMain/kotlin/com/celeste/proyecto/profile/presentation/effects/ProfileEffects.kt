package com.celeste.proyecto.profile.presentation.effects

sealed interface ProfileEffects {
    data object NavigateToEditProfile : ProfileEffects
    data object NavigateToUserInformation : ProfileEffects
    data object NavigateToSignIn : ProfileEffects
}