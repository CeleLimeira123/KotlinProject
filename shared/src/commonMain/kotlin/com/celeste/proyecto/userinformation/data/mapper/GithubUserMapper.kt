package com.celeste.proyecto.userinformation.data.mapper

import com.celeste.proyecto.userinformation.data.dto.GithubUserDto
import com.celeste.proyecto.userinformation.domain.model.GithubUserModel

fun GithubUserDto.toDomain(): GithubUserModel = GithubUserModel(
    alias = login ?: "",
    email = email ?: "",
    company = company ?: "",
    avatarUrl = avatarUrl ?: "",
)