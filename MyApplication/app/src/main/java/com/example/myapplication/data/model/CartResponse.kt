package com.example.myapplication.data.model

data class CartResponse(
    val success: Boolean,
    val data: List<CartItem>
)



data class CartData(
    val items: List<CartItem>
)


