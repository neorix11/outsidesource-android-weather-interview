package com.outsidesource.outsidesourceweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyForecast
import com.outsidesource.outsidesourceweatherapp.ui.login.WeatherlyLogin
import com.outsidesource.outsidesourceweatherapp.ui.theme.WeatherlyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainLayoutContainer()
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Login : Screen("Login", R.string.login_screen)
    object Forecast : Screen("Forecast", R.string.forecast_screen)
}

@Composable
fun MainLayoutContainer() {

    val navController = rememberNavController()

    WeatherlyTheme {
        NavHost(navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) {
                WeatherlyLogin()
            }

            composable(Screen.Forecast.route) {
                WeatherlyForecast {
                    navController.popBackStack()
                }
            }
        }
    }
}
