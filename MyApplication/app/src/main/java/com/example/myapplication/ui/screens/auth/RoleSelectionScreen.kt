package com.example.myapplication.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController

@Composable
fun RoleSelectionScreen(navController: NavHostController) {

    val PrimaryTeal = Color(0xFF008080)
    val BgColor = Color(0xFFF5F7F8)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Trywana",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryTeal
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate("login/user")
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue as User")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate("login/seller")
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue as Seller")
            }
        }
    }
}
