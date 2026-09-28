package com.celeste.proyecto.crossref.presentation.effects

sealed interface CrossrefEffects {
    data class OpenUrl(val url: String) : CrossrefEffects
}