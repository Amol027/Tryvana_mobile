package com.example.myapplication.data.model

data class CartItem(
    val id: Int,
    val user_id: Int,
    val product_id: Int,
    val quantity: Int,
    val title: String,
    val price: String,
    val image_url: String?
)
