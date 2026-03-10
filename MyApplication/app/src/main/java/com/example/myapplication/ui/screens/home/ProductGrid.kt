package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data.model.Product
import com.example.myapplication.ui.screens.home.components.ProductCard

// ProductGrid.kt (Updated)

@Composable
fun ProductGrid(
    products: List<Product>,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {

    if (products.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = androidx.compose.ui.Alignment.Center) {
            androidx.compose.material3.Text("No products found")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // Height fix: infinite constraint issue se bachaega
            .padding(horizontal = 12.dp)
    ) {
        products.chunked(2).forEach { rowProducts ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowProducts.forEach { product ->
                    ProductCard(
                        product = product,
                        rootNavController = rootNavController,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
