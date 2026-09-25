package com.celeste.proyecto.signin.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.signin.domain.usecase.SignInUseCase
import com.celeste.proyecto.signin.domain.vo.Email
import com.celeste.proyecto.signin.domain.vo.Password
import com.celeste.proyecto.signin.presentation.effects.SignInEffects
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInVM(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SignInEffects>()
    val effect: SharedFlow<SignInEffects> = _effect.asSharedFlow()

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }
            is SignInEvents.OnPasswordChanged -> {
                _uiState.update { it.copy(pass = event.pass) }
            }
            is SignInEvents.OnSignInClicked -> {
                signIn()
            }
            is SignInEvents.OnDismissError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun signIn() {
        val email = Email(_uiState.value.email)
        val pass = Password(_uiState.value.pass)

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            signInUseCase(email, pass).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                    _effect.emit(SignInEffects.NavigateToHome)
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message ?: "Error al iniciar sesión")
                    }
                },
            )
        }
    }
}