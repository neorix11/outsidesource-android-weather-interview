package com.outsidesource.outsidesourceweatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


/**
 * Example for using light and dark themes
 */
/*
primary: Color = Color(0xFF6200EE),
    primaryVariant: Color = Color(0xFF3700B3),
    secondary: Color = Color(0xFF03DAC6),
    secondaryVariant: Color = Color(0xFF018786),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
 */
private val LightThemeColors = lightColors(
    primary = SeaFoamGreen,
    primaryVariant = SeaFoamGreen,
    secondary = SkyBlue,
    secondaryVariant = SkyBlue,
    background = Color.White,
    surface = Color.White
)

private val DarkThemeColors = darkColors(
    primary = SeaFoamGreen,
    primaryVariant = SeaFoamGreen,
    secondary = SkyBlue,
    background = Color.White,
    surface = Color.White
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