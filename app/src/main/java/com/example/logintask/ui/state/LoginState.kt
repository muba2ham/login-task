package com.example.logintask.ui.state

import com.example.logintask.utils.states.LoginStates

data class LoginState(
    var email: String = "",
    var password: String = "",
    var loginState: String = LoginStates.Undetermined.state,
    var loginUIState: Boolean = true,
    var logoutUIState: Boolean = true
)