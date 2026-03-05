package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MainRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.myapplication.data.model.Product
import android.content.Context
import android.app.Application


class MainViewModel (application: Application) : ViewModel() {

    private val repo = MainRepository(application)

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadProducts() {
        viewModelScope.launch {
            try {
                isLoading = true
                products = repo.getAllProducts()
            } catch (e: Exception) {
                e.printStackTrace()
                products = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}


