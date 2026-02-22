package com.example.myapplication.data.model

data class ProductData(
    val page: Int,
    val products: List<Product>
)

data class ProductResponse(
    val success: Boolean,
    val data: ProductData
)
