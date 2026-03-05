package com.example.myapplication.data.model
import com.google.gson.annotations.SerializedName
data class AddToCartRequest(
    @SerializedName("product_id") // 👈 Ye annotation check karo
    val productId: Int,
    val quantity: Int
)