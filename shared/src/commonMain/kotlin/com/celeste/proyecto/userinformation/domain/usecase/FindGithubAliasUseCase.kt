package com.celeste.proyecto.userinformation.domain.usecase
import com.celeste.proyecto.userinformation.domain.model.GithubUserModel
import com.celeste.proyecto.userinformation.domain.repository.GithubRepository

class FindGithubAliasUseCase(private val repository: GithubRepository) {
    suspend operator fun invoke(alias: String): Result<GithubUserModel> {
        return repository.findByAlias(alias)
    }
}