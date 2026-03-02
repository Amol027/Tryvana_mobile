package com.example.myapplication.data.model

data class RegisterResponse(
    val success: Boolean,
    val message: String?,
    val data: RegisterData
)

data class RegisterData(
    val token: String,
    val user: User
)
