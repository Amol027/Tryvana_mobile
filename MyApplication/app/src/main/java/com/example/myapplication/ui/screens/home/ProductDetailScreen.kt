package com.example.myapplication.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.ProductViewModel

private val PrimaryTeal = Color(0xFF008080)
private val AccentCoral = Color(0xFFFF6F61)
private val BgColor = Color(0xFFF5F7F8)

@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    productId: Int
) {
    val product = productViewModel.products.find { it.id == productId }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // ================= HEADER =================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryTeal)
                    .padding(vertical = 18.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "← Trywana",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                Text("❤️", color = Color.White)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ================= SCROLLABLE CONTENT =================
            product?.let {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    // IMAGE
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = it.image_url,
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // TITLE
                    Text(
                        text = it.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // PRICE
                    Text(
                        text = "₹${it.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryTeal
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    // DESCRIPTION
                    Text(
                        text = it.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(100.dp)) // bottom button ke liye space
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Product not found", color = Color.Gray)
                }
            }
        }

        // ================= ADD TO CART BUTTON =================
        product?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp, vertical = 70.dp) // bottom nav ke upar
            ) {
                Button(
                    onClick = { /* TODO: Add cart functionality */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentCoral),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // ================= BOTTOM NAV =================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "🏠", color = PrimaryTeal, modifier = Modifier.clickable { navController.navigate("home") }
            )
            Text(
                "🔳", color = Color.Gray, modifier = Modifier.clickable { navController.navigate("categories") }
            )
            Text(
                "🛍️", color = Color.Gray, modifier = Modifier.clickable { navController.navigate("orders") }
            )
            Text(
                "👤", color = Color.Gray, modifier = Modifier.clickable { navController.navigate("profile") }
            )
        }
    }
}
