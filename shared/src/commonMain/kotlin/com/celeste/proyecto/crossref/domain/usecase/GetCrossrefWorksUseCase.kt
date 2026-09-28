package com.celeste.proyecto.crossref.domain.usecase

import com.celeste.proyecto.crossref.domain.model.CrossrefArticleModel
import com.celeste.proyecto.crossref.domain.repository.CrossrefRepository

class GetCrossrefWorksUseCase(private val repository: CrossrefRepository) {
    suspend operator fun invoke(query: String = "machine learning", rows: Int = 3): Result<List<CrossrefArticleModel>> {
        return repository.searchWorks(query, rows)
    }
}