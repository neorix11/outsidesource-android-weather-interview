package com.outsidesource.outsidesourceweatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.loadVectorResource
import com.outsidesource.outsidesourceweatherapp.R
import com.outsidesource.outsidesourceweatherapp.ui.theme.backgroundGradient

@Composable
fun WeatherlyLogin(buttonPressed: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    backgroundGradient,
                    0.0F,
                    5000.0F
                )
            )
    ) {
        Row {
            val image = loadVectorResource(id = R.drawable.weatherly_logo)
            image.resource.resource?.let {
                Image(imageVector = it)
            }
        }

        Row {
            Button(onClick = buttonPressed) {
                Text("This is navigation")
            }
        }

    }


}