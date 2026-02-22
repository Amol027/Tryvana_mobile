package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*
import com.example.myapplication.ui.viewmodel.UserViewModel
import com.example.myapplication.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel
) {

    val name = userViewModel.userName
    val email = userViewModel.userEmail

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
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = email,
                color = TextLight
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

        // ===== Logout Card =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 6.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            TextButton(
                onClick = {

                    // ✅ Proper logout
                    authViewModel.logout()
                    userViewModel.clearUser()

                    // ✅ Navigate to Login & clear full backstack
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
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
