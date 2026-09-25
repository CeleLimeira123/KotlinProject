package com.celeste.proyecto.userinformation.presentation.state

sealed interface UserInformationEvents {
    data class OnSearchAliasChanged(val alias: String) : UserInformationEvents
    data object OnSearchClicked : UserInformationEvents
    data object LoadUserInformation : UserInformationEvents
    data object OnDismissError : UserInformationEvents
}