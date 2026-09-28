package com.celeste.proyecto.catalog.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val results: List<MovieDto>? = null,
)