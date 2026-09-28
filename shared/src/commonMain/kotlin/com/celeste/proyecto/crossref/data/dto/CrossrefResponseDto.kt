package com.celeste.proyecto.crossref.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrossrefResponseDto(
    @SerialName("status") val status: String? = null,
    @SerialName("message") val message: CrossrefMessageDto? = null,
)

@Serializable
data class CrossrefMessageDto(
    @SerialName("items") val items: List<CrossrefWorkDto>? = null,
)

@Serializable
data class CrossrefWorkDto(
    @SerialName("title") val title: List<String>? = null,
    @SerialName("author") val author: List<CrossrefAuthorDto>? = null,
    @SerialName("published") val published: CrossrefDateDto? = null,
    @SerialName("published-print") val publishedPrint: CrossrefDateDto? = null,
    @SerialName("published-online") val publishedOnline: CrossrefDateDto? = null,
    @SerialName("container-title") val containerTitle: List<String>? = null,
    @SerialName("DOI") val doi: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("URL") val url: String? = null,
)

@Serializable
data class CrossrefAuthorDto(
    @SerialName("given") val given: String? = null,
    @SerialName("family") val family: String? = null,
)

@Serializable
data class CrossrefDateDto(
    @SerialName("date-parts") val dateParts: List<List<Int>>? = null,
)