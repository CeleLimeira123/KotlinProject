package com.celeste.proyecto.signup.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.celeste.proyecto.signup.presentation.state.SignUpEvents
import com.celeste.proyecto.signup.presentation.state.SignUpState

@Composable
fun SignUpScreen(
    state: SignUpState = SignUpState(),
    onEvent: (SignUpEvents) -> Unit = {},
    onNavigateToSignIn: () -> Unit = {},
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF6750A4),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Completa tus datos para registrarte",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        // Campo Nombre
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(SignUpEvents.OnNameChanged(it)) },
            label = { Text("Nombre completo") },
            placeholder = { Text("Tu nombre") },
            singleLine = true,
            leadingIcon = { Text("👤", modifier = Modifier.padding(start = 8.dp)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        )

        // Campo Correo
        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(SignUpEvents.OnEmailChanged(it)) },
            label = { Text("Correo electrónico") },
            placeholder = { Text("ejemplo@correo.com") },
            singleLine = true,
            leadingIcon = { Text("✉", modifier = Modifier.padding(start = 8.dp)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        )

        // Campo Contraseña
        OutlinedTextField(
            value = state.pass,
            onValueChange = { onEvent(SignUpEvents.OnPasswordChanged(it)) },
            label = { Text("Contraseña") },
            placeholder = { Text("********") },
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
            onClick = { onEvent(SignUpEvents.OnSignUpClicked) },
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
            Text(
                text = "Registrarse",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Fila inferior para volver a Login
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "¿Ya tienes una cuenta? ",
                style = MaterialTheme.typography.bodyMedium,
            )
            TextButton(onClick = onNavigateToSignIn) {
                Text(
                    text = "Inicia sesión",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6750A4),
                )
            }
        }
    }
}