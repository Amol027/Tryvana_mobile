package com.example.myapplication.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.*
import com.example.myapplication.data.network.TokenManager
import com.example.myapplication.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = MainRepository(application)
    private val api = repo.api
    private val tokenManager = TokenManager(application)

    /* ================= PRODUCTS ================= */

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    /* ================= CART STATE ================= */

    // Aise declare karo (Private mutable state aur Public immutable state)
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

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

                _isLoading.value = true

                val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
                val descBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceBody = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockBody = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

                var imagePart: MultipartBody.Part? = null

                if (imageUri != null) {

                    val inputStream = context.contentResolver.openInputStream(imageUri)
                    val bytes = inputStream?.readBytes()

                    val requestFile = bytes?.toRequestBody("image/*".toMediaTypeOrNull())

                    imagePart = requestFile?.let {
                        MultipartBody.Part.createFormData("image", "product.jpg", it)
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

                    message = "Product added successfully"
                    Log.d("ADD_PRODUCT", "Success")

                } else {

                    message = "Error adding product: ${response.code()}"
                    Log.e("ADD_PRODUCT", "Error: ${response.code()}")
                }

            } catch (e: Exception) {

                message = e.message ?: "Something went wrong"
                Log.e("ADD_PRODUCT", "Exception: ${e.message}")

            } finally {

                _isLoading.value = false
            }
        }
    }

    /* ================= LOAD PRODUCTS ================= */

    fun loadProducts() {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                products = repo.getAllProducts()

            } catch (e: Exception) {

                message = e.message

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun loadSellerProducts(token: String) {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                val response = repo.getMyProducts(token)
                products = response.data.products

            } catch (e: Exception) {

                Log.e("ProductViewModel", "Error fetching seller products: ${e.message}")
                products = emptyList()

            } finally {

                _isLoading.value = false
            }
        }
    }

    /* ================= ADD TO CART ================= */

    fun addToCart(productId: Int, quantity: Int) {

        viewModelScope.launch {

            val token = tokenManager.getToken()

            if (token.isNullOrEmpty()) {
                Log.e("CART", "Token not found")
                return@launch
            }

            try {

                val request = AddToCartRequest(productId, quantity)

                val response = api.addToCart("Bearer $token", request)

                if (response.success) {

                    Log.d("CART", "Added to cart")
                    getCart()

                } else {

                    Log.e("CART", "Failed: ${response.message}")
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    /* ================= GET CART ================= */

    /* ================= GET CART ================= */
    /* ================= GET CART ================= */
    fun getCart() {
        viewModelScope.launch {
            try {
                _isLoading.value = true // ✅ Fix: Use .value for StateFlow
                val token = tokenManager.getToken() ?: return@launch
                val response = api.getCart("Bearer $token")

                if (response.success) {
                    _cartItems.value = response.data // ✅ Fix: Use .value
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", e.message.toString())
            } finally {
                _isLoading.value = false // ✅ Fix: Use .value
            }
        }
    }

    /* ================= REMOVE CART ITEM ================= */

    fun removeFromCart(productId: Int) {

        viewModelScope.launch {

            try {

                val token = tokenManager.getToken() ?: return@launch

                val response = api.removeFromCart("Bearer $token", productId)

                if (response.success) {

                    Log.d("CART", "Removed item")
                    getCart()

                } else {

                    Log.e("CART", response.message)

                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    /* ================= UPDATE CART ================= */

    fun updateCartItem(productId: Int, newQuantity: Int) {

        viewModelScope.launch {

            if (newQuantity < 1) return@launch

            removeFromCart(productId)
            addToCart(productId, newQuantity)
        }
    }

    /* ================= BUY CART ================= */

    fun buyCart() {

        viewModelScope.launch {

            try {

                val token = tokenManager.getToken() ?: return@launch

                val response = api.buyCart("Bearer $token")

                if (response.success) {

                    Log.d("ORDER", "Order placed")
                    _cartItems.value = emptyList()

                } else {

                    Log.e("ORDER", response.message)
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}
