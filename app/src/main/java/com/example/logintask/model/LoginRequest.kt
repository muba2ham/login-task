package com.example.logintask.model

import retrofit2.http.Query

data class LoginRequest(@Query("email") val email: String, @Query("password") val password: String)
