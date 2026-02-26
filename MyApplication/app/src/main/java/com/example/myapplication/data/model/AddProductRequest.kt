package com.example.myapplication.data.model

data class AddProductRequest(
    val title: String,
    val description: String,
    val price: Double,
    val category: String
)
