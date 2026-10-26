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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.resources.Res
import com.celeste.proyecto.resources.common_back
import com.celeste.proyecto.resources.user_info_placeholder
import com.celeste.proyecto.resources.user_info_search_button
import com.celeste.proyecto.resources.user_info_title
import com.celeste.proyecto.userinformation.presentation.state.UserInformationEvents
import com.celeste.proyecto.userinformation.presentation.state.UserInformationState
import org.jetbrains.compose.resources.stringResource

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
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(onClick = { onEvent(UserInformationEvents.OnBackClicked) }) {
                Text(stringResource(Res.string.common_back))
            }
        }

        Text(
            text = stringResource(Res.string.user_info_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                        value = state.searchAlias,
                        onValueChange = { onEvent(UserInformationEvents.OnSearchAliasChanged(it)) },
                        label = { Text(stringResource(Res.string.user_info_placeholder)) },
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
                            Text(stringResource(Res.string.user_info_search_button))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        state.githubUser?.let { githubUser ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GitHub: ${githubUser.alias}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Empresa: ${githubUser.company.ifBlank { "-" }}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Email: ${githubUser.email.ifBlank { "-" }}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.localInfo?.let { info ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Info",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = info.address, style = MaterialTheme.typography.bodyMedium)
                    Text(text = info.phone, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
