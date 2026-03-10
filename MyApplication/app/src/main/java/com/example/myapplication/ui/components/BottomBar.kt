package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.myapplication.ui.theme.*

@Composable
fun BottomBar(
    bottomNavController: NavController,
    currentRoute: String?,
    role: String
) {

    val homeRoute = if (role.lowercase() == "seller") {
        "seller_home"
    } else {
        "user_home"
    }

    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            BottomItem(Icons.Outlined.Home, homeRoute, "Home", bottomNavController, currentRoute)

            BottomItem(Icons.Outlined.GridView, "categories", "Categories", bottomNavController, currentRoute)

            BottomItem(Icons.Outlined.ShoppingBag, "orders", "Orders", bottomNavController, currentRoute)

            BottomItem(Icons.Outlined.Person, "profile", "Profile", bottomNavController, currentRoute)
        }
    }
}

@Composable
fun BottomItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    route: String,
    label: String,
    bottomNavController: NavController,
    currentRoute: String?
) {

    val isSelected = currentRoute == route

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {

            if (currentRoute != route) {
                bottomNavController.navigate(route) {

                    popUpTo(bottomNavController.graph.findStartDestination().id) {
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isSelected) BgColor else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) PrimaryTeal else TextLight
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) PrimaryTeal else TextLight
        )
    }
}
