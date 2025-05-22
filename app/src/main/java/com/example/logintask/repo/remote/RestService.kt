package com.example.logintask.repo.remote

import com.example.logintask.model.LoginRequest
import com.example.logintask.model.LoginResponse
import com.example.logintask.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestService {

    @POST(Constants.API_ENDPOINT_LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

}
