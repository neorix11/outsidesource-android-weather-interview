package com.outsidesource.outsidesourceweatherapp.ui.components

import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
fun WeatherlyButton(
        text: String,
        enabled: Boolean = false,
        onClick: () -> Unit
) {
    Button(onClick = onClick, enabled = enabled) { Text(text) }
}