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
import com.example.myapplication.ui.viewmodel.UserViewModel
import com.example.myapplication.ui.theme.*

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by authViewModel.loginSuccess
    val isLoading by authViewModel.isLoading
    val errorMessage by authViewModel.errorMessage

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {

            // 🔥 SET REAL USER DATA HERE
            userViewModel.setUser(
                id = authViewModel.loggedInUserId ?: 0,
                name = authViewModel.loggedInUserName ?: "Trywana User",
                email = authViewModel.loggedInUserEmail ?: email
            )


            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Trywana",
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryTeal
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text("Login", color = Color.White)
            }
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = { navController.navigate("register") }
        ) {
            Text("Don't have account? Register", color = PrimaryTeal)
        }
    }
}
