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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.core.localization.LanguageManager
import com.celeste.proyecto.core.theme.FontSizeScale
import com.celeste.proyecto.core.theme.ThemeManager
import com.celeste.proyecto.core.theme.ThemeMode
import com.celeste.proyecto.profile.presentation.state.ProfileEvents
import com.celeste.proyecto.profile.presentation.state.ProfileState
import com.celeste.proyecto.resources.Res
import com.celeste.proyecto.resources.language_en
import com.celeste.proyecto.resources.language_es
import com.celeste.proyecto.resources.profile_favorites_count
import com.celeste.proyecto.resources.profile_favorites_title
import com.celeste.proyecto.resources.profile_language_title
import com.celeste.proyecto.resources.profile_logout_button
import com.celeste.proyecto.resources.profile_title
import com.celeste.proyecto.resources.profile_user_prefix
import com.celeste.proyecto.resources.user_info_placeholder
import com.celeste.proyecto.resources.user_info_search_button
import com.celeste.proyecto.resources.user_info_title
import com.celeste.proyecto.userinformation.presentation.state.UserInformationEvents
import com.celeste.proyecto.userinformation.presentation.state.UserInformationState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(
    state: ProfileState = ProfileState(),
    onEvent: (ProfileEvents) -> Unit = {},
    userInformationState: UserInformationState = UserInformationState(),
    onUserInformationEvent: (UserInformationEvents) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val languageManager: LanguageManager = koinInject()
    val currentLang by languageManager.currentLanguage.collectAsState()

    val themeManager: ThemeManager = koinInject()
    val currentThemeMode by themeManager.themeMode.collectAsState()
    val currentFontSizeScale by themeManager.fontSizeScale.collectAsState()

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
                    text = stringResource(Res.string.profile_title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
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
                                text = stringResource(Res.string.profile_user_prefix, profile.name),
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de Tema (Sistema, Claro, Oscuro, Alto Contraste)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "🎨 Tema / Theme",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Button(
                                    onClick = { themeManager.setThemeMode(ThemeMode.SYSTEM) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentThemeMode == ThemeMode.SYSTEM) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("Auto", style = MaterialTheme.typography.labelSmall)
                                }

                                Button(
                                    onClick = { themeManager.setThemeMode(ThemeMode.LIGHT) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentThemeMode == ThemeMode.LIGHT) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("Claro", style = MaterialTheme.typography.labelSmall)
                                }

                                Button(
                                    onClick = { themeManager.setThemeMode(ThemeMode.DARK) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentThemeMode == ThemeMode.DARK) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("Oscuro", style = MaterialTheme.typography.labelSmall)
                                }

                                Button(
                                    onClick = { themeManager.setThemeMode(ThemeMode.HIGH_CONTRAST) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentThemeMode == ThemeMode.HIGH_CONTRAST) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("Contraste", style = MaterialTheme.typography.labelSmall)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Tamaños de Letra
                            Text(
                                text = "🔤 Tamaño de letra / Font Size",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Button(
                                    onClick = { themeManager.setFontSizeScale(FontSizeScale.SMALL) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentFontSizeScale == FontSizeScale.SMALL) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("A-", style = MaterialTheme.typography.labelSmall)
                                }

                                Button(
                                    onClick = { themeManager.setFontSizeScale(FontSizeScale.NORMAL) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentFontSizeScale == FontSizeScale.NORMAL) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("A", style = MaterialTheme.typography.labelSmall)
                                }

                                Button(
                                    onClick = { themeManager.setFontSizeScale(FontSizeScale.LARGE) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentFontSizeScale == FontSizeScale.LARGE) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("A+", style = MaterialTheme.typography.labelSmall)
                                }

                                Button(
                                    onClick = { themeManager.setFontSizeScale(FontSizeScale.EXTRA_LARGE) },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentFontSizeScale == FontSizeScale.EXTRA_LARGE) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text("A++", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de Idioma (Español / English)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(Res.string.profile_language_title),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Button(
                                    onClick = { languageManager.setLanguage("es") },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentLang == "es") ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text(stringResource(Res.string.language_es))
                                }

                                Button(
                                    onClick = { languageManager.setLanguage("en") },
                                    modifier = Modifier.weight(1f),
                                    colors = if (currentLang == "en") ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                                ) {
                                    Text(stringResource(Res.string.language_en))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Búsqueda de Usuarios GitHub Integrada
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(Res.string.user_info_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                OutlinedTextField(
                                    value = userInformationState.searchAlias,
                                    onValueChange = { onUserInformationEvent(UserInformationEvents.OnSearchAliasChanged(it)) },
                                    label = { Text(stringResource(Res.string.user_info_placeholder)) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                )

                                Button(
                                    onClick = { onUserInformationEvent(UserInformationEvents.OnSearchClicked) },
                                    enabled = !userInformationState.isLoading,
                                    modifier = Modifier.padding(start = 8.dp),
                                ) {
                                    Text(stringResource(Res.string.user_info_search_button))
                                }
                            }

                            userInformationState.githubUser?.let { gh ->
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(text = "GitHub: ${gh.alias}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Empresa: ${gh.company.ifBlank { "-" }}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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
                                text = stringResource(Res.string.profile_favorites_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = stringResource(Res.string.profile_favorites_count, 12),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón rojo de Cerrar Sesión
                    Button(
                        onClick = { onEvent(ProfileEvents.OnSignOutClicked) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                    ) {
                        Text(stringResource(Res.string.profile_logout_button))
                    }
                }
            }
        }
    }
}
