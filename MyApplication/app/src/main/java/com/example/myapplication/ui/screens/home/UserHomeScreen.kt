package com.example.myapplication.ui.screens.home

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.ui.components.BottomBar
import androidx.compose.ui.Modifier

@Composable
fun UserHomeScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {

    val products = productViewModel.products

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }


}


