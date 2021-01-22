package com.outsidesource.outsidesourceweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.AnimBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.*
import androidx.navigation.navOptions
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyForecast
import com.outsidesource.outsidesourceweatherapp.ui.forecast.ForecastViewModel
import com.outsidesource.outsidesourceweatherapp.ui.login.LoginViewModel
import com.outsidesource.outsidesourceweatherapp.ui.login.WeatherlyLogin
import com.outsidesource.outsidesourceweatherapp.ui.theme.WeatherlyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


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

    /*
    Currently navigation transitions are unavailable via Navigation Components for Compose :(
     */
    WeatherlyTheme {
        NavHost(navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) {
                WeatherlyLogin {
                    navController.navigate(Screen.Forecast.route)
                }
            }

            composable(Screen.Forecast.route) {
                WeatherlyForecast {
                    navController.popBackStack()
                }
            }
        }
    }
}
