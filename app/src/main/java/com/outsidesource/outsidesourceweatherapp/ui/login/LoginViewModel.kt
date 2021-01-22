package com.outsidesource.outsidesourceweatherapp.ui.login

import androidx.lifecycle.ViewModel
import com.outsidesource.outsidesourceweatherapp.models.Credentials
import com.outsidesource.outsidesourceweatherapp.util.Outcome

class LoginViewModel : ViewModel() {

    fun authenticateUser(credentials: Credentials): Outcome<String> {
        return validateCredentials(credentials)
    }

    private fun validateCredentials(credentials: Credentials): Outcome<String> {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (credentials.email.matches(emailPattern.toRegex()) && credentials.password.length > 4) {
            Outcome.Ok("Good")
        } else {
            Outcome.Error(if (!credentials.email.matches(emailPattern.toRegex())) "Invalid Email" else "Invalid Password")
        }
    }
}