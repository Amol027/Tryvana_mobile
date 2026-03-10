package com.example.myapplication.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.ui.components.BottomBar

private val Primary = Color(0xFF008080)
private val Accent = Color(0xFFFF6F61)
private val Bg = Color(0xFFF5F7F8)

@Composable
fun CartScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {

    val cartItems by productViewModel.cartItems.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.getCart()
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                bottomNavController = navController,
                currentRoute = "cart",
                role = "buyer"
            )
        }
        ,
        containerColor = Bg
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "My Cart",
                style = MaterialTheme.typography.headlineMedium,
                color = Primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }

            } else if (cartItems.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty")
                }

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {

                    items(cartItems) { item ->

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column {

                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "₹${item.price}",
                                        color = Primary,
                                        style = MaterialTheme.typography.titleSmall
                                    )

                                    Text(
                                        text = "Qty: ${item.quantity}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                Button(
                                    onClick = {
                                        productViewModel.removeFromCart(item.product_id)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Accent
                                    )
                                ) {
                                    Text("Remove")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { productViewModel.buyCart() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Proceed to Buy")
                }
            }
        }
    }
}
