package com.example.logintask.repo.local

import android.content.SharedPreferences
import com.example.logintask.utils.Constants
import javax.inject.Inject

class LocalService @Inject constructor(
    private val sharedPreferences: SharedPreferences) {

    fun getUserLoginState(): Boolean {
        return sharedPreferences.getBoolean(Constants.STORAGE_LOGIN_STATE, false)
    }

    fun setUserLoginState(state: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(Constants.STORAGE_LOGIN_STATE, state)
            apply()
        }
    }

    fun getUserLoginToken(): String {
        return sharedPreferences.getString(Constants.STORAGE_LOGIN_TOKEN, "")!!
    }

    fun setUserLoginToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(Constants.STORAGE_LOGIN_TOKEN, token)
            apply()
        }
    }

}