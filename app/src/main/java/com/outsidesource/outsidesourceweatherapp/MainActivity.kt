package com.outsidesource.outsidesourceweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyForecast
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyLogin
import com.outsidesource.outsidesourceweatherapp.ui.theme.WeatherlyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainLayoutContainer()
        }
    }
}

sealed class Screen(val title: String) {
    object Login : Screen("Login")
    object Forecast : Screen("Forecast")
}

@Composable
fun MainLayoutContainer() {

    val navController = rememberNavController()

    WeatherlyTheme {
        NavHost(navController, startDestination = Screen.Login.title) {
            composable(Screen.Login.title) {
                WeatherlyLogin {
                    navController.navigate(Screen.Forecast.title)
                }
            }

            composable(Screen.Forecast.title) {
                WeatherlyForecast {
                    navController.popBackStack()
                }
            }
        }
    }
}
