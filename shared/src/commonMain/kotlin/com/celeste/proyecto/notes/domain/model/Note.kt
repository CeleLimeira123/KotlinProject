package com.celeste.proyecto.notes.domain.model

data class Note(
    val id: String = "",
    val text: String = "",
    val createdAt: Long = 0L,
)
