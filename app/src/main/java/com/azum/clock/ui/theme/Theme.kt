package com.azum.clock.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Azum Clock always favors a true-black minimalist look for the clock,
// but exposes a standard Material3 theme wrapper for the rest of the app.
private val AzumDarkColors = darkColorScheme(
    primary = AzumWhite,
    background = AzumBlack,
    surface = AzumBlack,
    onPrimary = AzumBlack,
    onBackground = AzumWhite,
    onSurface = AzumWhite
)

private val AzumLightColors = lightColorScheme(
    primary = AzumBlack,
    background = AzumWhite,
    surface = AzumWhite,
    onPrimary = AzumWhite,
    onBackground = AzumBlack,
    onSurface = AzumBlack
)

@Composable
fun AzumClockTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) AzumDarkColors else AzumLightColors
    MaterialTheme(
        colorScheme = colors,
        typography = AzumTypography,
        content = content
    )
}
