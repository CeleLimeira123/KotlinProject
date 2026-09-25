package com.celeste.proyecto.signin.presentation.effects

sealed interface SignInEffects {
    data object NavigateToHome : SignInEffects
    data class ShowToast(val message: String) : SignInEffects
}