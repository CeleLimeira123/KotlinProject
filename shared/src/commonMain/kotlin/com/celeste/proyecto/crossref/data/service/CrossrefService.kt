package com.celeste.proyecto.crossref.data.service

import com.celeste.proyecto.crossref.data.dto.CrossrefResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CrossrefService(
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
) {
    suspend fun fetchWorks(query: String = "machine learning", rows: Int = 3): CrossrefResponseDto {
        val response = client.get("https://api.crossref.org/works") {
            parameter("query", query)
            parameter("rows", rows)
        }
        return response.body<CrossrefResponseDto>()
    }
}