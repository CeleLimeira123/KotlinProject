package com.celeste.proyecto.userinformation.data.service

import com.celeste.proyecto.userinformation.data.datasource.GithubRemoteDataSource
import com.celeste.proyecto.userinformation.data.dto.GithubUserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class GitHubApiService(
    private val client: HttpClient = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
            )
        }
    },
) : GithubRemoteDataSource {

    override suspend fun getUser(nickname: String): GithubUserDto {
        val response = client.get("https://api.github.com/users/$nickname")
        return response.body<GithubUserDto>()
    }
}