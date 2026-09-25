package com.celeste.proyecto.profile.presentation.state

sealed interface ProfileEvents {
    data object LoadProfile : ProfileEvents
    data object OnEditProfileClicked : ProfileEvents
    data object OnSearchUsersClicked : ProfileEvents
    data object OnSignOutClicked : ProfileEvents
}