package com.outsidesource.outsidesourceweatherapp.ui.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun WeatherlyForecast(onButtonPressed: () -> Unit) {
    Button(onClick = onButtonPressed) {
        Text("GO BACK")
    }
}