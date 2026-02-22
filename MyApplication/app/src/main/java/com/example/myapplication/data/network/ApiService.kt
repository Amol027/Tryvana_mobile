package com.example.myapplication.data.network

import com.example.myapplication.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @GET("api/products/all")
    suspend fun getAllProducts(): ProductResponse


}
