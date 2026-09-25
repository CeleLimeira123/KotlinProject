package com.celeste.proyecto.profile.presentation.state

import com.celeste.proyecto.profile.domain.model.ProfileModel

data class ProfileState(
    val profile: ProfileModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)