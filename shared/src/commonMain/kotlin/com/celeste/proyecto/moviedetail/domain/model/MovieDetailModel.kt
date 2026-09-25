package com.celeste.proyecto.moviedetail.domain.model

data class MovieDetailModel(
    val id: String,
    val title: String,
    val overview: String,
    val rating: Double,
    val releaseDate: String,
)