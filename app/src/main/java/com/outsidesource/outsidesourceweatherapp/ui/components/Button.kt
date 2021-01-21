package com.outsidesource.outsidesourceweatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outsidesource.outsidesourceweatherapp.ui.theme.CharredBlack

@Composable
fun WeatherlyButton(
    text: String,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(50),
            modifier = Modifier.preferredSize(120.dp, 45.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CharredBlack),
            enabled = enabled
        ) {
            Text(text = text, color = Color.White)
        }
    }

}

@Preview
@Composable
fun PreviewButton() {
    WeatherlyButton(text = "Test", onClick = { })
}