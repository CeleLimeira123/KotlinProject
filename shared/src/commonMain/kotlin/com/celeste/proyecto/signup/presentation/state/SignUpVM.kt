package com.celeste.proyecto.signup.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.signup.domain.usecase.SignUpUseCase
import com.celeste.proyecto.signup.presentation.effects.SignUpEffects
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpVM(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SignUpEffects>()
    val effect: SharedFlow<SignUpEffects> = _effect.asSharedFlow()

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnNameChanged -> _uiState.update { it.copy(name = event.name) }
            is SignUpEvents.OnEmailChanged -> _uiState.update { it.copy(email = event.email) }
            is SignUpEvents.OnPasswordChanged -> _uiState.update { it.copy(pass = event.pass) }
            is SignUpEvents.OnSignUpClicked -> signUp()
            is SignUpEvents.OnDismissError -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun signUp() {
        val name = _uiState.value.name
        val email = _uiState.value.email
        val pass = _uiState.value.pass

        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, llena todos los campos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                signUpUseCase(email, pass, name)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                _effect.emit(SignUpEffects.NavigateToHome)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error en el registro") }
            }
        }
    }
}