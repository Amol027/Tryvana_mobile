package com.example.myapplication.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String = "USER" // default USER
)
