package com.celeste.proyecto.core.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun DsCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val cardModifier = if (onClick != null) {
        modifier
            .clickable(onClick = onClick)
            .semantics { role = Role.Button }
    } else {
        modifier
    }

    val border = if (MaterialTheme.colorScheme.outline != MaterialTheme.colorScheme.surface) {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    } else {
        null
    }

    Card(
        shape = DsTokens.RadiusLarge,
        colors = colors,
        border = border,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = cardModifier,
    ) {
        Column(modifier = Modifier.padding(DsTokens.SpacingLarge)) {
            content()
        }
    }
}
