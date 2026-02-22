package com.example.myapplication.data.model

data class Product(
    val id: Int,
    val seller_id: Int,
    val title: String,
    val description: String,
    val price: String,
    val stock: Int,
    val category: String?,
    val image_url: String?,
    val created_at: String
)

//data class ProductData(
//    val page: Int,
//    val products: List<Product>
//)
//
//data class ProductResponse(
//    val success: Boolean,
//    val data: ProductData
//)
