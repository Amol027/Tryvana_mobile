package com.example.myapplication.ui.screens.product

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.ui.components.BottomBar

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
    var quantity by remember { mutableStateOf(1) }

    // ✅ User token from SharedPreferences
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MyAppPref", Context.MODE_PRIVATE)
    val userToken = sharedPref.getString("token", "") ?: ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

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
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = it.image_url,
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryTeal
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // STOCK TEXT
                    Text(
                        text = "In Stock",
                        color = Color(0xFF2E7D32),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // QUANTITY SELECTOR
                    Text(
                        text = "Quantity",
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedButton(
                            onClick = { if (quantity > 1) quantity-- },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("-")
                        }

                        Text(
                            text = quantity.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        OutlinedButton(
                            onClick = { quantity++ },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("+")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // DESCRIPTION
                    Text(
                        text = it.description ?: "No Description Available",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(140.dp))
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

        // ================= ACTION BUTTONS =================
        product?.let {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 70.dp, start = 20.dp, end = 20.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    // ADD TO CART
                    OutlinedButton(
                        onClick = {
                            // 1️⃣ Call addToCart
                            productViewModel.addToCart(product.id, quantity)

                            navController.navigate("cart") {
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 14.dp)
                    ) {
                        Text("Add to Cart")
                    }


                    // BUY NOW
//                    Button(
//                        onClick = {
//                            productViewModel.buyNow(userToken, it.id, quantity)
//
//                        },
//                        modifier = Modifier.weight(1f),
//                        colors = ButtonDefaults.buttonColors(containerColor = AccentCoral),
//                        shape = RoundedCornerShape(12.dp),
//                        contentPadding = PaddingValues(vertical = 14.dp)
//                    ) {
//                        Text(
//                            "Buy Now",
//                            color = Color.White,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
                }
            }
        }

        // ================= BOTTOM NAV =================
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomBar(
                bottomNavController = navController,
                currentRoute = "detail/$productId",
                role = "user"
            )
        }
    }
}
