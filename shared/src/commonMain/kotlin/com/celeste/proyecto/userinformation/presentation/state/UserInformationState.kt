package com.celeste.proyecto.userinformation.presentation.state

import com.celeste.proyecto.userinformation.domain.model.GithubUserModel
import com.celeste.proyecto.userinformation.domain.model.UserInformationModel

data class UserInformationState(
    val searchAlias: String = "",
    val githubUser: GithubUserModel? = null,
    val localInfo: UserInformationModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)