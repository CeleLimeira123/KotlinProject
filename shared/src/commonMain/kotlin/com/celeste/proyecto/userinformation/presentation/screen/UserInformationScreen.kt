package com.celeste.proyecto.userinformation.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.userinformation.presentation.state.UserInformationEvents
import com.celeste.proyecto.userinformation.presentation.state.UserInformationState

@Composable
fun UserInformationScreen(
    state: UserInformationState = UserInformationState(),
    onEvent: (UserInformationEvents) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Búsqueda GitHub & Información",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        // Sección de Búsqueda de Usuario GitHub
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Buscar Usuario en GitHub",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = state.searchAlias,
                        onValueChange = { onEvent(UserInformationEvents.OnSearchAliasChanged(it)) },
                        label = { Text("Alias / Username") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onEvent(UserInformationEvents.OnSearchClicked) },
                        enabled = !state.isLoading,
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

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de Error (por ejemplo, para el 404 de GitHub)
        if (state.error != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                ),
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            ) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

        // Tarjeta con Datos de GitHub si existe resultado
        state.githubUser?.let { githubUser ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resultado de GitHub",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Alias: ${githubUser.alias}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "Empresa: ${if (githubUser.company.isBlank()) "No especificada" else githubUser.company}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Email: ${if (githubUser.email.isBlank()) "No público" else githubUser.email}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Avatar URL: ${githubUser.avatarUrl}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Datos del Usuario Local
        state.localInfo?.let { info ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Información Local",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Dirección: ${info.address}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Teléfono: ${info.phone}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Fecha de Nacimiento: ${info.birthDate}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}