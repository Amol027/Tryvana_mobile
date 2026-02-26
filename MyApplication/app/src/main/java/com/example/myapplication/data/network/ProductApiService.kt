package com.example.myapplication.data.network

import com.example.myapplication.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ProductApiService {

    @POST("products")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body request: AddProductRequest
    ): Response<SellerProductsResponse>

    @GET("products/my")
    suspend fun getMyProducts(
        @Header("Authorization") token: String
    ): Response<List<Product>>
}
