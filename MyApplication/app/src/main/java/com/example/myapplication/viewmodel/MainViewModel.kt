package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.repository.MainRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class MainViewModel : ViewModel() {

    private val repo = MainRepository()

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadProducts() {
        viewModelScope.launch {
            try {
                isLoading = true
                // ✅ Direct assignment, no isSuccessful/body()
                products = repo.getProducts()
            } catch (e: Exception) {
                e.printStackTrace()
                products = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}

