package com.outsidesource.outsidesourceweatherapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherlyTextField(
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.Transparent,
    placeholder: String = "",
    textFieldType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    requestFocus: FocusRequester
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 24.dp, bottom = 24.dp), horizontalArrangement = Arrangement.Center
    ) {
        val textState = remember { mutableStateOf(TextFieldValue()) }

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = textFieldType, imeAction = imeAction),
            value = textState.value,
            onValueChange = { valueChanged -> textState.value = valueChanged },
            placeholder = { Text(text = placeholder) },
            leadingIcon = {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {

                    }
                )
            },
            onImeActionPerformed = { imeAction, softwareKeyboardController ->
                println("PERFORMED $imeAction")
                when (imeAction) {
                    ImeAction.Done -> softwareKeyboardController?.hideSoftwareKeyboard()
                    ImeAction.Next -> requestFocus.requestFocus()
                    else -> softwareKeyboardController?.hideSoftwareKeyboard()
                }

            },
            visualTransformation = if (textFieldType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(requestFocus),
            activeColor = activeColor,
            inactiveColor = inactiveColor,
            shape = RoundedCornerShape(1.dp),
            backgroundColor = Color.White,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        )
    }
}