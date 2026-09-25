package com.celeste.proyecto.userinformation.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celeste.proyecto.userinformation.domain.usecase.FindGithubAliasUseCase
import com.celeste.proyecto.userinformation.domain.usecase.GetUserInformationUseCase
import com.celeste.proyecto.userinformation.presentation.effects.UserInformationEffects
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInformationVM(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val findGithubAliasUseCase: FindGithubAliasUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserInformationState())
    val uiState: StateFlow<UserInformationState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<UserInformationEffects>()
    val effect: SharedFlow<UserInformationEffects> = _effect.asSharedFlow()

    init {
        loadInfo()
    }

    fun onEvent(event: UserInformationEvents) {
        when (event) {
            is UserInformationEvents.OnSearchAliasChanged -> {
                _uiState.update { it.copy(searchAlias = event.alias) }
            }
            is UserInformationEvents.OnSearchClicked -> {
                searchGithubUser()
            }
            is UserInformationEvents.LoadUserInformation -> {
                loadInfo()
            }
            is UserInformationEvents.OnDismissError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun loadInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val data = getUserInformationUseCase()
                _uiState.update { it.copy(localInfo = data, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al cargar información") }
            }
        }
    }

    private fun searchGithubUser() {
        val alias = _uiState.value.searchAlias.trim()
        if (alias.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, ingresa un alias de GitHub") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, githubUser = null) }
            findGithubAliasUseCase(alias).fold(
                onSuccess = { user ->
                    _uiState.update { it.copy(isLoading = false, githubUser = user, error = null) }
                    _effect.emit(UserInformationEffects.ShowToast("Usuario encontrado: ${user.alias}"))
                },
                onFailure = { throwable ->
                    _uiState.update { it.copy(isLoading = false, githubUser = null, error = throwable.message) }
                },
            )
        }
    }
}