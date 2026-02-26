package com.example.myapplication.data.model

data class SellerProductsResponse(
    val success: Boolean,
    val data: ProductsData
)

data class ProductsData(
    val products: List<Product>
)
