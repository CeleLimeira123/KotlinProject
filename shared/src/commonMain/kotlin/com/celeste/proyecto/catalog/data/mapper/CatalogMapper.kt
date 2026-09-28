package com.celeste.proyecto.catalog.data.mapper

import com.celeste.proyecto.catalog.data.dto.CatalogDto
import com.celeste.proyecto.catalog.data.dto.MovieDto
import com.celeste.proyecto.catalog.domain.model.MovieModel

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toDomain(): MovieModel {
    val fullPosterUrl = if (!posterPath.isNullOrEmpty()) {
        "$TMDB_IMAGE_BASE_URL$posterPath"
    } else {
        null
    }
    return MovieModel(
        id = id ?: 0,
        title = title ?: "",
        posterPath = fullPosterUrl,
    )
}

fun CatalogDto.toDomainList(): List<MovieModel> {
    return results?.map { it.toDomain() } ?: emptyList()
}