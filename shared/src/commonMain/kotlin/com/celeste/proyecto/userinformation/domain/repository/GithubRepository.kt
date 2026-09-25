package com.celeste.proyecto.userinformation.domain.repository

import com.celeste.proyecto.userinformation.domain.model.GithubUserModel

interface GithubRepository {
    suspend fun findByAlias(alias: String): Result<GithubUserModel>
}