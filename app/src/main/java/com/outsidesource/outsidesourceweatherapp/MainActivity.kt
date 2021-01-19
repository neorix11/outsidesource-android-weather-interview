package com.outsidesource.outsidesourceweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WeatherlyButton("Get Weather", true) { println("CLICKED BUTTON") }}
    }
}
