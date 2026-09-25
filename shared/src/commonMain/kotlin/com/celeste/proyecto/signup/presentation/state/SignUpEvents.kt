package com.celeste.proyecto.signup.presentation.state

sealed interface SignUpEvents {
    data class OnNameChanged(val name: String) : SignUpEvents
    data class OnEmailChanged(val email: String) : SignUpEvents
    data class OnPasswordChanged(val pass: String) : SignUpEvents
    data object OnSignUpClicked : SignUpEvents
    data object OnDismissError : SignUpEvents
}