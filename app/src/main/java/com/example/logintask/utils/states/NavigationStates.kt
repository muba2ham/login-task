package com.example.logintask.utils.states

sealed class NavigationStates {

    object NavigateToApp : NavigationStates()
    object NavigateToLogin : NavigationStates()

}