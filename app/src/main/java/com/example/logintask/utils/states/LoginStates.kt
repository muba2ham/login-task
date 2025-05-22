package com.example.logintask.utils.states

import com.example.logintask.utils.UIStrings

enum class LoginStates(val state: String) {
    Undetermined(""),
    Error(UIStrings.login_error),
    Success(UIStrings.login_success)
}