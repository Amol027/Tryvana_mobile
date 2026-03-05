package com.example.myapplication.data.repository

import android.content.Context
import android.net.Uri
import com.example.myapplication.data.model.*
import com.example.myapplication.data.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import  com.example.myapplication.data.network.TokenManager
class MainRepository (val context: Context) {

    private val tokenManager = TokenManager(context)
    val api = RetrofitClient.getApi(tokenManager)

    suspend fun login(email: String, password: String, role: String) =
        api.login(LoginRequest(email, password, role))

    suspend fun register(name: String, email: String, phone: String, password: String, role: String) =
        api.register(RegisterRequest(name, email, phone, password, role))

    suspend fun getMyProducts(token: String): SellerProductsResponse {
        return api.getMyProducts("Bearer $token")
    }

    suspend fun getAllProducts(page: Int = 1, search: String = ""): List<Product> {
        val response = api.getAllProducts(page, search)
        return if (response.success) {
            response.data.products
        } else {
            emptyList()
        }
    }

    suspend fun addProduct(
        context: Context,
        token: String,
        title: String,
        description: String,
        price: Double,
        stock: Int,
        category: String,
        imageUri: Uri?
    ): Boolean {
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val descBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceBody = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val stockBody = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

        var imagePart: MultipartBody.Part? = null
        if (imageUri != null) {
            val file = File(imageUri.path!!)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
        }

        val response = api.addProduct(
            "Bearer $token",
            titleBody,
            descBody,
            priceBody,
            stockBody,
            categoryBody,
            imagePart
        )

        return response.isSuccessful
    }
}
