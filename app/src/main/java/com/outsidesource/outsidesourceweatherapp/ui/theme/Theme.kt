package com.outsidesource.outsidesourceweatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


/**
 * Example for using light and dark themes
 */
private val LightThemeColors = lightColors(
    primary = SeaFoamGreen,
    secondary = SkyBlue
)

private val DarkThemeColors = darkColors(
    primary = SeaFoamGreen,
    secondary = SkyBlue
)

@Composable
fun WeatherlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography(),
        shapes = WeatherlyShapes,
        content = content
    )
}