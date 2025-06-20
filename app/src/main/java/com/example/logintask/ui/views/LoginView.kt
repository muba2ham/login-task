package com.example.logintask.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import com.example.logintask.ui.viewmodels.MainActivityViewModel
import com.example.logintask.utils.App
import com.example.logintask.utils.Login
import com.example.logintask.utils.UIStrings
import com.example.logintask.utils.states.NavigationStates

@Composable
fun LoginView(
    viewModel: MainActivityViewModel,
    navController: NavController) {

    val state by viewModel.loginStateModel.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.setEmail(it) },
            label = { Text(UIStrings.email) },
            placeholder = { Text(UIStrings.email_placeholder) },
            singleLine = true,
            enabled = state.loginUIState
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.setPassword(it) },
            label = { Text(UIStrings.password) },
            placeholder = { Text(UIStrings.password_placeholder) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = state.loginUIState
        )
        Text(text = state.loginState)
        Button(
            onClick = { viewModel.logUserIn() },
            enabled = state.loginUIState
        )
        {
            Text(UIStrings.login_button)
        }

    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationStates.NavigateToApp -> {
                    navController.navigate(App) {
                        popUpTo(Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                else -> Unit
            }
        }
    }

}