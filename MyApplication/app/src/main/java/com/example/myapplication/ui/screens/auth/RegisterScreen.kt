package com.example.myapplication.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.AuthViewModel

private val PrimaryTeal = Color(0xFF008080)
private val AccentCoral = Color(0xFFFF6F61)
private val BgColor = Color(0xFFF5F7F8)

@Composable
fun RegisterScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by authViewModel.loginSuccess
    val isLoading by authViewModel.isLoading
    val errorMessage by authViewModel.errorMessage

    // 🔥 Observe Login Success (Auto Login After Register)
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Trywana",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = PrimaryTeal
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        "Register",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Phone
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() &&
                                email.isNotBlank() &&
                                phone.isNotBlank() &&
                                password.isNotBlank()
                            ) {
                                authViewModel.register(name, email, phone, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentCoral
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Register", color = Color.White)
                        }
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(it, color = Color.Red, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    TextButton(
                        onClick = { navController.navigate("login") }
                    ) {
                        Text(
                            "Already have account? Login",
                            color = PrimaryTeal
                        )
                    }
                }
            }
        }
    }
}
