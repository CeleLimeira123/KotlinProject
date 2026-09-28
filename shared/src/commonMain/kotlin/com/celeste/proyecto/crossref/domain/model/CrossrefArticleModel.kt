package com.celeste.proyecto.crossref.domain.model

data class CrossrefArticleModel(
    val title: String,
    val author: String,
    val publishedDate: String,
    val containerTitle: String,
    val doi: String,
    val type: String,
    val url: String,
)