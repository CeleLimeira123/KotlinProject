package com.celeste.proyecto.userinformation.data.repository

import com.celeste.proyecto.userinformation.data.datasource.GithubRemoteDataSource
import com.celeste.proyecto.userinformation.data.mapper.toDomain
import com.celeste.proyecto.userinformation.domain.model.GithubUserModel
import com.celeste.proyecto.userinformation.domain.repository.GithubRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

class GithubRepositoryImpl(
    private val remoteDataSource: GithubRemoteDataSource,
) : GithubRepository {

    override suspend fun findByAlias(alias: String): Result<GithubUserModel> {
        return try {
            val dto = remoteDataSource.getUser(alias)
            Result.success(dto.toDomain())
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                Result.failure(Exception("El usuario de GitHub '$alias' no existe."))
            } else {
                Result.failure(Exception("Error de servidor (${e.response.status.value}): ${e.message}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Error al conectar con GitHub."))
        }
    }
}