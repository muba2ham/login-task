package com.example.logintask.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintask.model.LoginRequest
import com.example.logintask.repo.local.LocalService
import com.example.logintask.repo.remote.RestService
import com.example.logintask.ui.state.LoginState
import com.example.logintask.utils.Constants
import com.example.logintask.utils.states.LoginStates
import com.example.logintask.utils.states.NavigationStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val restService: RestService?,
    private val localService: LocalService
): ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationStates>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _loginStateModel = MutableStateFlow<LoginState>(LoginState())
    val loginStateModel: StateFlow<LoginState> = _loginStateModel

    fun setEmail(value: String) {
        _loginStateModel.update { currentState ->
            currentState.copy(email = value)
        }
    }

    fun setPassword(value: String) {
        _loginStateModel.update { currentState ->
            currentState.copy(password = value)
        }
    }

    fun logUserIn() {
        if (!loginStateModel.value.email.isEmpty() && !loginStateModel.value.password.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {

                val response = restService!!.login(LoginRequest(loginStateModel.value.email, loginStateModel.value.password))

                if (response.isSuccessful) {
                    println("TOKEN ${response.body()!!.token}")

                    localService.setUserLoginState(true)
                    localService.setUserLoginToken(response.body()!!.token)

                    _loginStateModel.update { currentState ->
                        currentState.copy(
                            loginState = LoginStates.Success.state,
                            logoutUIState = true,
                            email = "",
                            password = ""
                        )
                    }

                    delay(Constants.COMPOSE_NAVIGATION_DELAY)
                    _navigationEvent.emit(NavigationStates.NavigateToApp)

                } else {
                    _loginStateModel.update { currentState ->
                        currentState.copy(
                            loginState = LoginStates.Error.state,
                            logoutUIState = true
                        )
                    }
                }

            }
        } else {
            _loginStateModel.update { currentState ->
                currentState.copy(
                    loginState = LoginStates.Error.state,
                    logoutUIState = true
                )
            }
        }
    }

    fun logUserOut() {
        viewModelScope.launch {
            localService.setUserLoginState(false)
            localService.setUserLoginToken("")

            _loginStateModel.update { currentState ->
                currentState.copy(
                    loginUIState = true,
                    loginState = LoginStates.Undetermined.state,
                    email = "",
                    password = ""
                )
            }

            delay(Constants.COMPOSE_NAVIGATION_DELAY)
            _navigationEvent.emit(NavigationStates.NavigateToLogin)
        }
    }
}