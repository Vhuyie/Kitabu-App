package com.example.kitabu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val KitabuColorScheme = lightColorScheme(
    primary = KitabuPink,
    onPrimary = KitabuWhite,

    primaryContainer = KitabuLightPink,
    onPrimaryContainer = KitabuDarkPink,

    secondary = KitabuDarkPink,
    onSecondary = KitabuWhite,

    background = KitabuBackground,
    onBackground = KitabuText,

    surface = KitabuWhite,
    onSurface = KitabuText,

    surfaceVariant = KitabuLightPink,
    onSurfaceVariant = KitabuSecondaryText
)

@Composable
fun KitabuTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KitabuColorScheme,
        typography = Typography(),
        content = content
    )
}