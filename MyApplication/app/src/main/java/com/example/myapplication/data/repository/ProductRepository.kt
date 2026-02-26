package com.example.myapplication.data.repository

import com.example.myapplication.data.model.*
import com.example.myapplication.data.network.ProductApiService
import retrofit2.Response

class ProductRepository(
    private val api: ProductApiService
) {

    suspend fun addProduct(token: String, request: AddProductRequest)
            : Response<SellerProductsResponse> {
        return api.addProduct("Bearer $token", request)
    }
}
