package com.celeste.proyecto.moviedetail.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.moviedetail.presentation.state.MovieDetailEvents
import com.celeste.proyecto.moviedetail.presentation.state.MovieDetailState

@Composable
fun MovieDetailScreen(
    state: MovieDetailState = MovieDetailState(),
    onEvent: (MovieDetailEvents) -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center),
            )
        } else {
            state.movieDetail?.let { detail ->
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    OutlinedButton(
                        onClick = { onEvent(MovieDetailEvents.OnBackClicked) },
                        modifier = Modifier.padding(bottom = 16.dp),
                    ) {
                        Text("⬅ Volver")
                    }

                    Text(text = detail.title, style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Rating: ⭐ ${detail.rating}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = detail.overview, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}