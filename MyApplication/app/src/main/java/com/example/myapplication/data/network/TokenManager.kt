package com.example.myapplication.data.network

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {



    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("TOKEN", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("TOKEN", null)
    }

    fun clearToken() {
        prefs.edit().remove("TOKEN").apply()
    }
}

