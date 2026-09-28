package com.celeste.proyecto.crossref.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.crossref.domain.model.CrossrefArticleModel
import com.celeste.proyecto.crossref.presentation.state.CrossrefEvents
import com.celeste.proyecto.crossref.presentation.state.CrossrefState

@Composable
fun CrossrefScreen(
    state: CrossrefState,
    onEvent: (CrossrefEvents) -> Unit = {},
    onRetry: () -> Unit = {},
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Artículos Académicos (Crossref)",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6750A4),
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { onEvent(CrossrefEvents.OnQueryChanged(it)) },
                    label = { Text("Búsqueda (Query)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    OutlinedTextField(
                        value = state.rows.toString(),
                        onValueChange = { input ->
                            val rowsVal = input.toIntOrNull() ?: 3
                            onEvent(CrossrefEvents.OnRowsChanged(rowsVal))
                        },
                        label = { Text("Resultados (Rows)") },
                        singleLine = true,
                        modifier = Modifier.width(120.dp),
                    )

                    Button(
                        onClick = { onEvent(CrossrefEvents.OnSearchClicked) },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.height(20.dp).width(20.dp),
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Text("Buscar")
                        }
                    }
                }
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color(0xFF6750A4))
            }
        } else if (state.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetry) {
                        Text("Reintentar")
                    }
                }
            }
        } else if (state.articles.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No hay resultados para mostrar",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.articles) { article ->
                    CrossrefArticleCard(
                        article = article,
                        onUrlClick = { url ->
                            if (url.isNotBlank()) {
                                try {
                                    uriHandler.openUri(url)
                                } catch (_: Exception) {}
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun CrossrefArticleCard(
    article: CrossrefArticleModel,
    onUrlClick: (String) -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "✍ Autor(es): ${article.author}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = " Publicación: ${article.containerTitle}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = "Fecha: ${article.publishedDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = "Tipo: ${article.type}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = "DOI: ${article.doi}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
            )

            if (article.url.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Url de la publicación: ${article.url}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onUrlClick(article.url) },
                )
            }
        }
    }
}