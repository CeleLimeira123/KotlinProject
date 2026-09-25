package com.celeste.proyecto.profile.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.profile.domain.usecase.GetProfileUseCase
import com.celeste.proyecto.profile.presentation.effects.ProfileEffects
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileVM(
    private val getProfileUseCase: GetProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileEffects>()
    val effect: SharedFlow<ProfileEffects> = _effect.asSharedFlow()

    init {
        loadProfile()
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.LoadProfile -> loadProfile()
            is ProfileEvents.OnEditProfileClicked -> {
                viewModelScope.launch { _effect.emit(ProfileEffects.NavigateToEditProfile) }
            }
            is ProfileEvents.OnSearchUsersClicked -> {
                viewModelScope.launch { _effect.emit(ProfileEffects.NavigateToUserInformation) }
            }
            is ProfileEvents.OnSignOutClicked -> {
                viewModelScope.launch { _effect.emit(ProfileEffects.NavigateToSignIn) }
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val p = getProfileUseCase()
                _uiState.update { it.copy(profile = p, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al cargar perfil") }
            }
        }
    }
}