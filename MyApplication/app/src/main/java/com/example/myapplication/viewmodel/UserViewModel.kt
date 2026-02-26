package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var userId by mutableStateOf(0)
        private set

    var userName by mutableStateOf("Guest User")
        private set

    var userEmail by mutableStateOf("guest@trywana.com")
        private set

    fun setUser(id: Int, name: String, email: String) {
        userId = id
        userName = name
        userEmail = email
    }

    fun clearUser() {
        userId = 0
        userName = "Guest User"
        userEmail = "guest@trywana.com"
    }
}
