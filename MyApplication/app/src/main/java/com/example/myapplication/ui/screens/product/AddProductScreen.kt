package com.example.myapplication.ui.screens.product

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.AuthViewModel

private val PrimaryTeal = Color(0xFF008080)
private val AccentCoral = Color(0xFFFF6F61)
private val BgColor = Color(0xFFF5F7F8)

@Composable
fun AddProductScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val token = authViewModel.loggedInUserToken ?: ""

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectedImageUri = it
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Add Product",
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryTeal
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Product Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Stock") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal)
        ) {
            Text("Select Image", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                val priceDouble = price.toDoubleOrNull() ?: 0.0
                val stockInt = stock.toIntOrNull() ?: 0

                if (title.isNotBlank() && priceDouble > 0) {

                    productViewModel.addProduct(
                        context = context,
                        token = token,
                        title = title,
                        description = description,
                        price = priceDouble,
                        stock = stockInt,
                        category = category,
                        imageUri = selectedImageUri
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentCoral)
        ) {
            Text("Upload Product", color = Color.White)
        }
    }
}
