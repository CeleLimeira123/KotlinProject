package com.celeste.proyecto.crossref.data.mapper

import com.celeste.proyecto.crossref.data.dto.CrossrefAuthorDto
import com.celeste.proyecto.crossref.data.dto.CrossrefDateDto
import com.celeste.proyecto.crossref.data.dto.CrossrefResponseDto
import com.celeste.proyecto.crossref.data.dto.CrossrefWorkDto
import com.celeste.proyecto.crossref.domain.model.CrossrefArticleModel

fun CrossrefWorkDto.toDomain(): CrossrefArticleModel {
    val articleTitle = title?.firstOrNull()?.takeIf { it.isNotBlank() } ?: "Sin título"

    val authorsList = author?.mapNotNull { formatAuthor(it) }?.filter { it.isNotBlank() }
    val authorString = if (!authorsList.isNullOrEmpty()) {
        authorsList.joinToString(", ")
    } else {
        "Autor no especificado"
    }

    val dateDto = published ?: publishedPrint ?: publishedOnline
    val formattedDate = formatDateParts(dateDto)

    val publication = containerTitle?.firstOrNull()?.takeIf { it.isNotBlank() } ?: "Sin publicación"

    return CrossrefArticleModel(
        title = articleTitle,
        author = authorString,
        publishedDate = formattedDate,
        containerTitle = publication,
        doi = doi ?: "Sin DOI",
        type = type ?: "No especificado",
        url = url ?: "",
    )
}

private fun formatAuthor(author: CrossrefAuthorDto): String? {
    val given = author.given?.trim().orEmpty()
    val family = author.family?.trim().orEmpty()
    val name = "$given $family".trim()
    return name.ifBlank { null }
}

private fun formatDateParts(dateDto: CrossrefDateDto?): String {
    val firstPart = dateDto?.dateParts?.firstOrNull() ?: return "Fecha no disponible"
    return when (firstPart.size) {
        1 -> "${firstPart[0]}"
        2 -> "${firstPart[1].toString().padStart(2, '0')}/${firstPart[0]}"
        3 -> "${firstPart[2].toString().padStart(2, '0')}/${firstPart[1].toString().padStart(2, '0')}/${firstPart[0]}"
        else -> "Fecha no disponible"
    }
}

fun CrossrefResponseDto.toDomainList(): List<CrossrefArticleModel> {
    return message?.items?.map { it.toDomain() } ?: emptyList()
}