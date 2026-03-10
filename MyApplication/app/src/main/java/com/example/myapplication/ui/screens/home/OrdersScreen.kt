package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.ProductViewModel

import com.example.myapplication.ui.components.BottomBar
import androidx.compose.material3.Scaffold


@Composable
fun OrdersScreen(navController: NavHostController) {

    Scaffold(
        bottomBar = {
            BottomBar(
                bottomNavController = navController,
                currentRoute = "orders",
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
                    text = "My Orders",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

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
                    onClick = { navController.navigate("user_home") }
                ) {
                    Text(
                        text = "Start Shopping",
                        color = PrimaryTeal
                    )
                }
            }
        }
    }
}


@Composable
fun AddProductScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel
) {

    var title by remember { mutableStateOf<String>("") }
    var description by remember { mutableStateOf<String>("") }
    var price by remember { mutableStateOf<String>("") }
    var category by remember { mutableStateOf<String>("") }

    val token = authViewModel.loggedInUserToken ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = { newValue -> title = newValue },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { newValue -> description = newValue },
            label = { Text(text = "Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { newValue -> price = newValue },
            label = { Text(text = "Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { newValue -> category = newValue },
            label = { Text(text = "Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val context = androidx.compose.ui.platform.LocalContext.current

        Button(
            onClick = {
                productViewModel.addProduct(
                    token = token,
                    title = title,
                    description = description,
                    price = price.toDoubleOrNull() ?: 0.0,
                    category = category,
                    context = context,
                    stock = 10,
                    imageUri = null

                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Product")
        }
    }
}
