package com.celeste.proyecto.crossref.presentation.state

import com.celeste.proyecto.crossref.domain.model.CrossrefArticleModel

data class CrossrefState(
    val searchQuery: String = "machine learning",
    val rows: Int = 3,
    val articles: List<CrossrefArticleModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)