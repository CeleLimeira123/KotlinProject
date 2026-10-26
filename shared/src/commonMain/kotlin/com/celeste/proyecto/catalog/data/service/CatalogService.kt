package com.celeste.proyecto.catalog.data.service

import com.celeste.proyecto.catalog.data.dto.CatalogDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CatalogService(
    private val apiKey: String,
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
    suspend fun fetchCatalog(): CatalogDto {
        val url = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=$apiKey"
        val response = client.get(url)
        return response.body<CatalogDto>()
    }
}
