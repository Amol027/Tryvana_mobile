package com.example.myapplication.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.AuthViewModel
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions

private val PrimaryTeal = Color(0xFF008080)
private val AccentCoral = Color(0xFFFF6F61)
private val BgColor = Color(0xFFF5F7F8)

@Composable
fun RegisterScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    role: String
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by authViewModel.loginSuccess
    val isLoading by authViewModel.isLoading
    val errorMessage by authViewModel.errorMessage

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("main/${role.lowercase()}") {
                popUpTo("role") { inclusive = true }
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (role.lowercase() == "seller") "Seller Register" else "User Register",
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryTeal
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                if (it.length <= 10 && it.all { ch -> ch.isDigit() }) {
                    phone = it
                }
            },
            label = { Text("Phone (10 digit)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (name.isNotBlank() &&
                    email.isNotBlank() &&
                    phone.length == 10 &&
                    password.isNotBlank()
                ) {
                    authViewModel.register(
                        name,
                        email,
                        phone,
                        password,
                        role.uppercase()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AccentCoral)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Register", color = Color.White)
            }
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = { navController.navigate("login/$role") }
        ) {
            Text("Already have account? Login", color = PrimaryTeal)
        }
    }
}


