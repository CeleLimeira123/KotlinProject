package com.celeste.proyecto.home.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.celeste.proyecto.catalog.domain.model.MovieModel
import com.celeste.proyecto.catalog.presentation.state.CatalogState
import com.celeste.proyecto.catalog.presentation.viewmodel.CatalogViewModel
import com.celeste.proyecto.resources.Res
import com.celeste.proyecto.resources.catalog_featured_category
import com.celeste.proyecto.resources.catalog_promo_banner
import com.celeste.proyecto.resources.common_error
import com.celeste.proyecto.resources.common_retry
import com.celeste.proyecto.resources.home_featured_count
import com.celeste.proyecto.resources.home_featured_title
import com.celeste.proyecto.resources.home_see_all
import com.celeste.proyecto.resources.home_welcome
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    catalogViewModel: CatalogViewModel,
    onMovieClick: (Int) -> Unit = {},
    onSeeAllMoviesClick: () -> Unit = {},
) {
    val uiState by catalogViewModel.uiState.collectAsState()
    val appConfig by catalogViewModel.appConfig.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        when (val state = uiState) {
            is CatalogState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CatalogState.Success -> {
                // Muestra ÚNICAMENTE 5 películas como máximo en Inicio
                val featuredMovies = state.movies.take(5)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Header de bienvenida y Dashboard
                    item(span = { GridItemSpan(2) }) {
                        Column {
                            Text(
                                text = stringResource(Res.string.home_welcome),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary,
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = appConfig.mensajeBienvenida,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Categoría destacada (Remote Config)
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.primaryContainer,
                            ) {
                                Text(
                                    text = stringResource(Res.string.catalog_featured_category, appConfig.categoriaDestacada.uppercase()),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                )
                            }
                        }
                    }

                    // Banner Promocional (Si mostrar_promo = true)
                    if (appConfig.mostrarPromo) {
                        item(span = { GridItemSpan(2) }) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                ),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(Res.string.catalog_promo_banner),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    )
                                }
                            }
                        }
                    }

                    // Fila de Encabezado de Películas Destacadas con "Ver todas"
                    item(span = { GridItemSpan(2) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = stringResource(Res.string.home_featured_title),
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                Text(
                                    text = stringResource(Res.string.home_featured_count, featuredMovies.size),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline,
                                )
                            }

                            TextButton(onClick = onSeeAllMoviesClick) {
                                Text(
                                    text = stringResource(Res.string.home_see_all) + " ➔",
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }

                    // Renderiza SOLAMENTE las 5 películas destacadas
                    items(featuredMovies) { movie ->
                        FeaturedMovieCard(
                            movie = movie,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
            }

            is CatalogState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(Res.string.common_error, state.message), color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { catalogViewModel.loadCatalog() }) {
                            Text(stringResource(Res.string.common_retry))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedMovieCard(
    movie: MovieModel,
    onMovieClick: (Int) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { onMovieClick(movie.id) },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!movie.posterPath.isNullOrBlank()) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "🎬",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
