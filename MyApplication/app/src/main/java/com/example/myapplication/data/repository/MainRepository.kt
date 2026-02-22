package com.example.myapplication.data.repository

import com.example.myapplication.data.model.*
import com.example.myapplication.data.network.RetrofitClient

class MainRepository {

    suspend fun login(email: String, password: String) =
        RetrofitClient.api.login(LoginRequest(email, password))

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ) =
        RetrofitClient.api.register(RegisterRequest(name, email, phone, password))

    suspend fun getProducts(): List<Product> {
        val response = RetrofitClient.api.getAllProducts()
        return if (response.success) {
            response.data.products
        } else {
            emptyList()
        }
    }

}
