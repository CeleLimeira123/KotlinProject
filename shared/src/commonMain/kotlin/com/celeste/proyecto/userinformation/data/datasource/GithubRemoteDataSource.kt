package com.celeste.proyecto.userinformation.data.datasource

import com.celeste.proyecto.userinformation.data.dto.GithubUserDto

interface GithubRemoteDataSource {
    suspend fun getUser(nickname: String): GithubUserDto
}