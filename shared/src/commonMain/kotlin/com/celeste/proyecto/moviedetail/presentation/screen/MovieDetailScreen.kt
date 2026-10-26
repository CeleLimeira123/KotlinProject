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
import com.celeste.proyecto.resources.Res
import com.celeste.proyecto.resources.common_back
import com.celeste.proyecto.resources.movie_detail_overview
import com.celeste.proyecto.resources.movie_detail_rating
import org.jetbrains.compose.resources.stringResource

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
                        Text(stringResource(Res.string.common_back))
                    }

                    Text(text = detail.title, style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(Res.string.movie_detail_rating, detail.rating.toString()),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(Res.string.movie_detail_overview, detail.title),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}
