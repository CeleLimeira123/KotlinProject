package com.celeste.proyecto.signin.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.celeste.proyecto.resources.Res
import com.celeste.proyecto.resources.signin_button
import com.celeste.proyecto.resources.signin_email_label
import com.celeste.proyecto.resources.signin_email_placeholder
import com.celeste.proyecto.resources.signin_no_account
import com.celeste.proyecto.resources.signin_pass_label
import com.celeste.proyecto.resources.signin_pass_placeholder
import com.celeste.proyecto.resources.signin_signup_link
import com.celeste.proyecto.resources.signin_subtitle
import com.celeste.proyecto.resources.signin_title
import com.celeste.proyecto.signin.presentation.effects.SignInEffects
import com.celeste.proyecto.signin.presentation.state.SignInEvents
import com.celeste.proyecto.signin.presentation.state.SignInState
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInScreen(
    state: SignInState = SignInState(),
    effect: SharedFlow<SignInEffects>,
    onEvent: (SignInEvents) -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        effect.collect { fx ->
            when (fx) {
                is SignInEffects.NavigateToHome -> {
                    onNavigateHome()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Título de la pantalla
        Text(
            text = stringResource(Res.string.signin_title),
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF6750A4),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.signin_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        // Campo Correo electrónico
        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(SignInEvents.OnEmailChanged(it)) },
            label = { Text(stringResource(Res.string.signin_email_label)) },
            placeholder = { Text(stringResource(Res.string.signin_email_placeholder)) },
            singleLine = true,
            leadingIcon = { Text("✉", modifier = Modifier.padding(start = 8.dp)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        )

        // Campo Contraseña
        OutlinedTextField(
            value = state.pass,
            onValueChange = { onEvent(SignInEvents.OnPasswordChanged(it)) },
            label = { Text(stringResource(Res.string.signin_pass_label)) },
            placeholder = { Text(stringResource(Res.string.signin_pass_placeholder)) },
            singleLine = true,
            leadingIcon = { Text("🔒", modifier = Modifier.padding(start = 8.dp)) },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Text(if (isPasswordVisible) "👁" else "🙈")
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
        )

        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
            )
        }

        // Botón principal
        Button(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6750A4),
            ),
            onClick = { onEvent(SignInEvents.OnSignInClicked) },
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
            Text(
                text = stringResource(Res.string.signin_button),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Fila inferior para ir a Registro
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(Res.string.signin_no_account),
                style = MaterialTheme.typography.bodyMedium,
            )
            TextButton(onClick = onNavigateToSignUp) {
                Text(
                    text = stringResource(Res.string.signin_signup_link),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6750A4),
                )
            }
        }
    }
}
