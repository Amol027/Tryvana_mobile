package com.example.myapplication.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.ProductViewModel

@Composable
fun CartScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {

    val cartItems by productViewModel.cartItems.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()

    // screen open hote hi cart load karo
    LaunchedEffect(Unit) {
        productViewModel.getCart()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("My Cart", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else if (cartItems.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Cart is empty")
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(cartItems) { item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(item.title)
                            Text("₹${item.price} x ${item.quantity}")
                        }

                        Button(
                            onClick = {
                                productViewModel.removeFromCart(item.product_id)
                            }
                        ) {
                            Text("Remove")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { productViewModel.buyCart() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buy Now")
            }
        }
    }
}

