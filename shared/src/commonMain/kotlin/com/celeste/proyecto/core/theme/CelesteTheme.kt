package com.celeste.proyecto.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF7D5260),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
)

val HighContrastColorScheme = lightColorScheme(
    primary = Color(0xFF000000),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF000000),
    secondary = Color(0xFF000000),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFFFFF),
    onSecondaryContainer = Color(0xFF000000),
    tertiary = Color(0xFF000000),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = Color(0xFF000000),
    outline = Color(0xFF000000),
)

fun scaledTypography(scale: Float): Typography {
    val base = Typography()
    return Typography(
        headlineLarge = base.headlineLarge.copy(fontSize = (32 * scale).sp),
        headlineMedium = base.headlineMedium.copy(fontSize = (28 * scale).sp),
        headlineSmall = base.headlineSmall.copy(fontSize = (24 * scale).sp),
        titleLarge = base.titleLarge.copy(fontSize = (22 * scale).sp),
        titleMedium = base.titleMedium.copy(fontSize = (16 * scale).sp),
        titleSmall = base.titleSmall.copy(fontSize = (14 * scale).sp),
        bodyLarge = base.bodyLarge.copy(fontSize = (16 * scale).sp),
        bodyMedium = base.bodyMedium.copy(fontSize = (14 * scale).sp),
        bodySmall = base.bodySmall.copy(fontSize = (12 * scale).sp),
        labelLarge = base.labelLarge.copy(fontSize = (14 * scale).sp),
        labelMedium = base.labelMedium.copy(fontSize = (12 * scale).sp),
        labelSmall = base.labelSmall.copy(fontSize = (11 * scale).sp),
    )
}

@Composable
fun CelesteAppTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    fontSizeScale: FontSizeScale = FontSizeScale.NORMAL,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.HIGH_CONTRAST -> false
    }

    val colorScheme = when {
        themeMode == ThemeMode.HIGH_CONTRAST -> HighContrastColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val typography = scaledTypography(fontSizeScale.scaleFactor)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content,
    )
}
