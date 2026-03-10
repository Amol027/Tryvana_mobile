package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.*
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import com.example.myapplication.ui.components.BottomBar


@Composable
fun CategoriesScreen(navController: NavHostController) {

    Scaffold(
        bottomBar = {
            BottomBar(
                bottomNavController = navController,
                currentRoute = "categories",
                role = "buyer"
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgColor)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryTeal)
                    .padding(vertical = 20.dp, horizontal = 15.dp)
            ) {
                Text(
                    text = "Categories",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val categories = listOf(
                "👕 Men's Fashion",
                "👗 Women's Fashion",
                "🧸 Kids Wear",
                "🎧 Electronics",
                "💄 Beauty"
            )

            categories.forEach { category ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(category, color = TextDark)
                        Text("❯", color = TextLight)
                    }
                }
            }
        }
    }
}

