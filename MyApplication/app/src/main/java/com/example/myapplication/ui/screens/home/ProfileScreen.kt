package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.util.Log
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.ui.theme.BgColor
import com.example.myapplication.ui.theme.PrimaryTeal
import com.example.myapplication.ui.theme.TextDark
import com.example.myapplication.ui.theme.TextLight

@Composable
fun ProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {

    val name = authViewModel.loggedInUserName ?: "Guest User"
    val email = authViewModel.loggedInUserEmail ?: "guest@trywana.com"

    Log.d("ProfileDebug", "ProfileScreen loaded - Name: $name, Email: $email")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {

        // ===== Header =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryTeal)
                .padding(vertical = 20.dp, horizontal = 15.dp)
        ) {
            Text(
                "Profile",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }

        // ===== User Info Section =====
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFDDDDDD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("👤", fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = email,
                color = TextLight,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        val items = listOf(
            "My Account",
            "Address Book",
            "Help Center"
        )

        items.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 6.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(item, color = TextDark)
                    Text("❯", color = TextLight)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ===== Logout =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 6.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            TextButton(
                onClick = {
                    authViewModel.logout()

                    navController.navigate("role") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Logout",
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
