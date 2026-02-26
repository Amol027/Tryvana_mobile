package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.example.myapplication.data.model.*
import com.example.myapplication.data.repository.MainRepository
import com.example.myapplication.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val repo = MainRepository()

    var loginSuccess = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    var loggedInUserId by mutableStateOf<Int?>(null)
    var loggedInUserName by mutableStateOf<String?>(null)
    var loggedInUserEmail by mutableStateOf<String?>(null)
    var loggedInUserToken by mutableStateOf<String?>(null)

    private val _loggedInUserRole = mutableStateOf<String?>(null)
    val loggedInUserRole: State<String?> get() = _loggedInUserRole

    lateinit var userViewModel: UserViewModel
    fun attachUserViewModel(userVM: UserViewModel) {
        userViewModel = userVM
    }

    fun login(email: String, password: String, role: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response: Response<LoginResponse> = repo.login(email, password, role)
                handleLoginResponse(response)
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage
            } finally {
                isLoading.value = false
            }
        }
    }

    fun register(name: String, email: String, phone: String, password: String, role: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response: Response<RegisterResponse> = repo.register(name, email, phone, password, role)
                handleRegisterResponse(response)
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful) {
            val data = response.body()?.data
            val user = data?.user

            loggedInUserId = user?.id
            loggedInUserName = user?.name
            loggedInUserEmail = user?.email
            loggedInUserToken = data?.token
            _loggedInUserRole.value = user?.role

            if (this@AuthViewModel::userViewModel.isInitialized && user != null) {
                userViewModel.setUser(user.id, user.name, user.email)
            }

            loginSuccess.value = true
        } else {
            errorMessage.value = "Login Failed: ${response.message()}"
        }
    }

    private fun handleRegisterResponse(response: Response<RegisterResponse>) {
        if (response.isSuccessful) {
            val data = response.body()?.data
            val user = data?.user

            loggedInUserId = user?.id
            loggedInUserName = user?.name
            loggedInUserEmail = user?.email
            loggedInUserToken = data?.token
            _loggedInUserRole.value = user?.role

            if (this@AuthViewModel::userViewModel.isInitialized && user != null) {
                userViewModel.setUser(user.id, user.name, user.email)
            }

            loginSuccess.value = true
        } else {
            errorMessage.value = "Register Failed: ${response.message()}"
        }
    }

    fun logout() {
        loginSuccess.value = false
        loggedInUserId = null
        loggedInUserName = null
        loggedInUserEmail = null
        loggedInUserToken = null
        _loggedInUserRole.value = null

        if (this@AuthViewModel::userViewModel.isInitialized) {
            userViewModel.clearUser()
        }
    }
}
