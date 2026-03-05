package com.example.myapplication.ui.screens.home

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.ui.components.BottomBar
import com.example.myapplication.data.model.Product
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.Alignment
import androidx.compose.runtime.collectAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerHomeScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel
) {

    val token = authViewModel.loggedInUserToken ?: ""
    val role by authViewModel.loggedInUserRole

    val products = productViewModel.products
    val isLoading by productViewModel.isLoading.collectAsState()


    // ✅ Refresh every time screen recomposes after coming back
    LaunchedEffect(Unit) {
        if (token.isNotEmpty() && role?.uppercase() == "SELLER") {
            productViewModel.loadSellerProducts(token)
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text("My Products") })
        },

        floatingActionButton = {
            if (role?.uppercase() == "SELLER") {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_product")  // ✅ FIXED ROUTE
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }
        },

        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = "sellerHome",
                role = role?.uppercase() ?: ""
            )
        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                !isLoading && products.isEmpty() -> {
                    Text(
                        text = "No products added yet.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(products) { product ->
                            ProductItem(product = product) {
                                navController.navigate("detail/${product.id}") // ✅ FIXED ROUTE
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Price: ₹${product.price}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
