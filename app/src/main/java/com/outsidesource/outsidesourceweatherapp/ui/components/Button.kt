package com.outsidesource.outsidesourceweatherapp.ui.components

import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WeatherlyButton(
        text: String,
        enabled: Boolean = false,
        onClick: () -> Unit
) {
    Button(onClick = onClick, enabled = enabled) { Text(text) }
}

@Preview
@Composable
fun PreviewButton() {
    WeatherlyButton(text = "Test", onClick = {  })
}