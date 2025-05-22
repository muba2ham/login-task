package com.example.logintask.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.logintask.ui.viewmodels.MainActivityViewModel
import com.example.logintask.utils.App
import com.example.logintask.utils.Login
import com.example.logintask.utils.UIStrings
import com.example.logintask.utils.states.NavigationStates

@Composable
fun MainView(
    viewModel: MainActivityViewModel,
    navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = UIStrings.main_app_view)
        Button(
            onClick = { viewModel.logUserOut() },
            enabled = viewModel.logoutUIState.collectAsState().value
        )
        {
            Text(UIStrings.logout_button)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationStates.NavigateToLogin -> {
                    navController.navigate(Login) {
                        popUpTo(App) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                else -> Unit
            }
        }
    }

}