package com.example.myapplication.data.network

import com.example.myapplication.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /* ================= AUTH ================= */

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>


    /* ================= ADD PRODUCT ================= */

    @Multipart
    @POST("api/products/add")
    suspend fun addProduct(
        @Header("Authorization") token: String,

        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("category") category: RequestBody,

        @Part image: MultipartBody.Part?
    ): Response<Unit>


    /* ================= SELLER PRODUCTS ================= */

    @GET("api/products/my") // <- correct route
    suspend fun getMyProducts(
        @Header("Authorization") token: String
    ): SellerProductsResponse


    /* ================= ALL PRODUCTS ================= */

    @GET("api/products/all")
    suspend fun getAllProducts(
        @Query("page") page: Int = 1,
        @Query("search") search: String = ""
    ): SellerProductsResponse
}
