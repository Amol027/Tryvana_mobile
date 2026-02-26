package com.example.myapplication.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.example.myapplication.data.repository.MainRepository
import kotlinx.coroutines.launch
import com.example.myapplication.data.model.Product
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import com.example.myapplication.data.network.RetrofitClient



class ProductViewModel : ViewModel() {

    private val repo = MainRepository()

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    private val api = RetrofitClient.api



    var isLoading by mutableStateOf(false)
        private set

    var message by mutableStateOf<String?>(null)
        private set

    /* ================= ADD PRODUCT ================= */

    fun addProduct(
        context: Context,
        token: String,
        title: String,
        description: String,
        price: Double,
        stock: Int,
        category: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {

            try {

                val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
                val descBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceBody = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockBody = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

                var imagePart: MultipartBody.Part? = null

                if (imageUri != null) {
                    val inputStream = context.contentResolver.openInputStream(imageUri)
                    val bytes = inputStream?.readBytes()
                    val requestFile =
                        bytes?.toRequestBody("image/*".toMediaTypeOrNull())

                    imagePart = requestFile?.let {
                        MultipartBody.Part.createFormData(
                            "image",
                            "product.jpg",
                            it
                        )
                    }
                }

                val response = api.addProduct(
                    token = "Bearer $token",
                    title = titleBody,
                    description = descBody,
                    price = priceBody,
                    stock = stockBody,
                    category = categoryBody,
                    image = imagePart
                )

                if (response.isSuccessful) {
                    Log.d("ADD_PRODUCT", "Success")
                } else {
                    Log.e("ADD_PRODUCT", "Error: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("ADD_PRODUCT", "Exception: ${e.message}")
            }
        }
    }

    /* ================= KEEP OLD FUNCTION NAMES ================= */

    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            products = repo.getAllProducts()
            isLoading = false
        }
    }

    fun loadSellerProducts(token: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repo.getMyProducts(token) // repo returns SellerProductsResponse
                products = response.data.products // <- correct mapping
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching seller products: ${e.message}")
                products = emptyList()
            } finally {
                isLoading = false
            }
        }
    }


}
