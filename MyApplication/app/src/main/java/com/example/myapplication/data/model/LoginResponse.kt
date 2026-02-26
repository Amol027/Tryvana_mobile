package com.example.myapplication.data.model

data class LoginResponse(
    val success: Boolean,
    val data: LoginData
)

data class LoginData(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)
