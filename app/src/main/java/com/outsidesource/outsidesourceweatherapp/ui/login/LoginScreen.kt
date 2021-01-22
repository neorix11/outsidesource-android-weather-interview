package com.outsidesource.outsidesourceweatherapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.loadVectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.outsidesource.outsidesourceweatherapp.R
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyButton
import com.outsidesource.outsidesourceweatherapp.ui.components.WeatherlyTextField
import com.outsidesource.outsidesourceweatherapp.ui.theme.backgroundGradient
import org.koin.androidx.compose.getViewModel

@Composable
fun WeatherlyLogin(
    loginViewModel: LoginViewModel = getViewModel(),
    onLoginSuccess: () -> Unit,
) {

    val openDialog = remember { mutableStateOf(false) }
    val requester = remember { FocusRequester() }
    val image = loadVectorResource(id = R.drawable.weatherly_logo)

    Column(
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

        Spacer(modifier = Modifier.preferredHeight(80.dp))

        image.resource.resource?.let {
            Image(imageVector = it, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.preferredHeight(80.dp))

        WeatherlyTextField(
            placeholder = "Email",
            textFieldType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            requestFocus = requester
        )
        WeatherlyTextField(
            placeholder = "Password",
            textFieldType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            requestFocus = requester
        )

        Spacer(modifier = Modifier.preferredHeight(240.dp))

        WeatherlyButton(
            text = "Login",
            onClick = {
                openDialog.value = true
            },
            enabled = true
        )

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = { Text("Error") },
                confirmButton = {
                    Button(onClick = { openDialog.value = false }) {
                        Text("Ok")
                    }
                }
            )
        }
    }
}

@Composable
private fun ErrorAlert(error: String) {


}