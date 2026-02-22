package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MainRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class AuthViewModel : ViewModel() {

    private val repo = MainRepository()

    // ================= UI STATES =================

    var loginSuccess = mutableStateOf(false)
        private set

    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set


    // ================= LOGGED USER DATA =================

    var loggedInUserId by mutableStateOf<Int?>(null)
        private set

    var loggedInUserName by mutableStateOf<String?>(null)
        private set

    var loggedInUserEmail by mutableStateOf<String?>(null)
        private set

    var loggedInUserToken by mutableStateOf<String?>(null)
        private set


    // ================= LOGIN =================

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                clearError()

                val response = repo.login(email, password)

                if (response.isSuccessful && response.body() != null) {

                    val body = response.body()!!

                    if (body.success) {

                        loggedInUserId = body.data.user.id
                        loggedInUserName = body.data.user.name
                        loggedInUserEmail = email   // backend email nahi bhej raha
                        loggedInUserToken = body.data.token

                        loginSuccess.value = true
                    } else {
                        errorMessage.value = "Invalid Credentials"
                    }

                } else {
                    errorMessage.value = "Login Failed"
                }

            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Server Error"
            } finally {
                isLoading.value = false
            }
        }
    }


    // ================= REGISTER (AUTO LOGIN ENABLED) =================

    fun register(name: String, email: String, phone: String, password: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                clearError()

                val response = repo.register(name, email, phone, password)

                if (response.isSuccessful) {

                    // 🔥 AUTO LOGIN AFTER REGISTER
                    login(email, password)

                } else {
                    errorMessage.value =
                        response.errorBody()?.string() ?: "Register Failed"
                }

            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Server Error"
            } finally {
                isLoading.value = false
            }
        }
    }


    // ================= LOGOUT =================

    fun logout() {

        loginSuccess.value = false

        loggedInUserId = null
        loggedInUserName = null
        loggedInUserEmail = null
        loggedInUserToken = null

        clearError()
    }


    // ================= HELPERS =================

    private fun clearError() {
        errorMessage.value = null
    }
}
