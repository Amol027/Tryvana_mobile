package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.*
import androidx.compose.ui.unit.sp


@Composable
fun OrdersScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {

        // 🔹 HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryTeal)
                .padding(vertical = 20.dp, horizontal = 15.dp)
        ) {
            Text(
                "My Orders",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        // 🔹 EMPTY STATE
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("📦", fontSize = 60.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Text("No orders yet!", color = TextLight)

            Spacer(modifier = Modifier.height(15.dp))

            TextButton(
                onClick = { navController.navigate("home") }
            ) {
                Text(
                    "Start Shopping",
                    color = PrimaryTeal
                )
            }
        }
    }
}
