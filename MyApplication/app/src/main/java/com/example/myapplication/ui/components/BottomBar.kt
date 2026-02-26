package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.*

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?,
    role: String
) {

    val homeRoute = if (role.lowercase() == "seller") {
        "seller_home"
    } else {
        "user_home"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        BottomItem("🏠", homeRoute, "Home", navController, currentRoute)
        BottomItem("🔳", "categories", "Categories", navController, currentRoute)
        BottomItem("🛍️", "orders", "Orders", navController, currentRoute)
        BottomItem("👤", "profile", "Profile", navController, currentRoute)
    }
}

@Composable
fun BottomItem(
    icon: String,
    route: String,
    label: String,
    navController: NavController,
    currentRoute: String?
) {

    val isSelected = currentRoute == route

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {

            if (currentRoute != route) {
                navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {

        Text(icon)

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) PrimaryTeal else TextLight,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
