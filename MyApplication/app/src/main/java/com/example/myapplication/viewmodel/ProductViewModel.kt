package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.repository.MainRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repo = MainRepository()

    // Compose states
    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadProducts() {
        viewModelScope.launch {
            try {
                isLoading = true
                println("🔥 Product API called")
                products = repo.getProducts()
                println("🔥 Products loaded: ${products.size}")
            } catch (e: Exception) {
                e.printStackTrace()
                products = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

}
