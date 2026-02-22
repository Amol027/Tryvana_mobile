package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.ui.viewmodel.UserViewModel

private val PrimaryTeal = Color(0xFF008080)
private val AccentCoral = Color(0xFFFF6F61)
private val BgColor = Color(0xFFF5F7F8)

@Composable
fun HomeScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    productViewModel: ProductViewModel
) {

    val products = productViewModel.products

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {

        LazyColumn {

            // ================= HEADER =================
            item {
                Column(
                    modifier = Modifier
                        .background(PrimaryTeal)
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Trywana",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        )
                        Text("❤️", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔍")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Search products...",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text("🎤")
                    }
                }
            }

            // ================= BANNER =================
            item {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFB2DFDB), Color.White)
                            )
                        )
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("FLASH SALE", fontWeight = FontWeight.Bold)
                        Text("Upto 70% Off!")
                    }
                    Text("🛍️", fontSize = 40.sp)
                }
            }

            // ================= CATEGORIES =================
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    val categories = listOf(
                        "👕" to "Men",
                        "👗" to "Women",
                        "📱" to "Gadgets",
                        "🏠" to "Home"
                    )

                    items(categories) { cat ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(55.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(2.dp, Color.LightGray, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(cat.first)
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(cat.second, fontSize = 11.sp)
                        }
                    }
                }
            }

            // ================= TITLE =================
            item {
                Text(
                    text = "Trending Now",
                    modifier = Modifier.padding(15.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            // ================= PRODUCT GRID =================
            items(products.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    rowProducts.forEach { product ->

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 15.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(3.dp)
                        ) {

                            Column {

                                // IMAGE AREA
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .background(Color(0xFFEEEEEE)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = product.image_url,
                                        contentDescription = product.title,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(10.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                // INFO
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {

                                    Text(
                                        text = "₹${product.price}",
                                        color = PrimaryTeal,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Button(
                                        onClick = {
                                            navController.navigate("detail/${product.id}")
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(36.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = AccentCoral
                                        ),
                                        shape = RoundedCornerShape(5.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text(
                                            text = "View",
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (rowProducts.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}
