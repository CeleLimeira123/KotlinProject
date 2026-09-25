package com.celeste.proyecto.signup.presentation.effects

sealed interface SignUpEffects {
    data object NavigateToHome : SignUpEffects
    data class ShowToast(val message: String) : SignUpEffects
}