package com.example.myapplication.data.model

data class Product(
    val id: Int,
    val title: String,
    val description: String?,
    val price: Double,
    val category: String?,
    val image_url: String?,
    val seller_id: Int
)
