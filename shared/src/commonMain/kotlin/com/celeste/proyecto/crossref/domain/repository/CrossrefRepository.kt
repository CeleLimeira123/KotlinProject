package com.celeste.proyecto.crossref.domain.repository

import com.celeste.proyecto.crossref.domain.model.CrossrefArticleModel

interface CrossrefRepository {
    suspend fun searchWorks(query: String = "machine learning", rows: Int = 3): Result<List<CrossrefArticleModel>>
}