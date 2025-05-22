package com.example.logintask.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.logintask.repo.local.LocalService
import com.example.logintask.ui.theme.MyLoginTaskTheme
import com.example.logintask.ui.viewmodels.MainActivityViewModel
import com.example.logintask.ui.views.LoginView
import com.example.logintask.ui.views.MainView
import com.example.logintask.utils.App
import com.example.logintask.utils.AppMainView
import com.example.logintask.utils.Credentials
import com.example.logintask.utils.Login
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    @Inject lateinit var localService: LocalService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLoggedIn = localService.getUserLoginState()

        enableEdgeToEdge()
        setContent {
            MyLoginTaskTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { _ ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) App else Login
                    ) {
                        navigation<Login>(
                            startDestination = Credentials
                        ) {
                            composable<Credentials> {
                                LoginView(viewModel, navController)
                            }
                        }
                        navigation<App>(
                            startDestination = AppMainView
                        ) {
                            composable<AppMainView> {
                                MainView(viewModel, navController)
                            }
                        }
                    }
                }
            }
        }
    }

}