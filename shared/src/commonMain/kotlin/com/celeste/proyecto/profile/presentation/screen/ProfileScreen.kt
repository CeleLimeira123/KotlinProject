package com.celeste.proyecto.profile.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.profile.presentation.state.ProfileEvents
import com.celeste.proyecto.profile.presentation.state.ProfileState

@Composable
fun ProfileScreen(
    state: ProfileState = ProfileState(),
    onEvent: (ProfileEvents) -> Unit = {},
) {
    val scrollState = rememberScrollState()

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Encabezado
                Text(
                    text = "Mi Perfil",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF6750A4),
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                state.profile?.let { profile ->
                    // Tarjeta de información del usuario
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "👤 ${profile.name}",
                                style = MaterialTheme.typography.titleLarge,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = profile.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Sección de Favoritos
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "⭐ Películas Favoritas",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = "12 guardadas",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botones organizados verticalmente
                    OutlinedButton(
                        onClick = { onEvent(ProfileEvents.OnEditProfileClicked) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                    ) {
                        Text("✏️  Editar Perfil")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { onEvent(ProfileEvents.OnSearchUsersClicked) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                    ) {
                        Text("🔍  Buscar Usuarios (GitHub)")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón rojo de Cerrar Sesión
                    Button(
                        onClick = { onEvent(ProfileEvents.OnSignOutClicked) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                    ) {
                        Text("🚪  Cerrar Sesión")
                    }
                }
            }
        }
    }
}