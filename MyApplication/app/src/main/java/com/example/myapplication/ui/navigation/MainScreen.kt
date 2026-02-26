package com.example.myapplication.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import com.example.myapplication.ui.components.BottomBar
import com.example.myapplication.ui.screens.home.HomeContent
import com.example.myapplication.ui.screens.home.ProfileScreen
import com.example.myapplication.ui.screens.home.OrdersScreen
import com.example.myapplication.ui.screens.home.CategoriesScreen
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.ui.screens.product.AddProductScreen

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel,
    role: String
) {

    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    val token = authViewModel.loggedInUserToken ?: ""

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = bottomNavController,
                currentRoute = currentRoute,
                role = role
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = bottomNavController,
            startDestination = if (role == "seller") "seller_home" else "user_home",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("user_home") {

                LaunchedEffect(Unit) {
                    productViewModel.loadProducts()
                }

                HomeContent(
                    navController = rootNavController,
                    products = productViewModel.products,
                    showAddButton = false
                )
            }

            composable("seller_home") {

                LaunchedEffect(token) {
                    if (token.isNotEmpty()) {
                        productViewModel.loadSellerProducts(token)  // ab token correct hai
                    }
                }


                HomeContent(
                    navController = rootNavController,
                    products = productViewModel.products,
                    showAddButton = true
                )
            }

            composable("profile") {
                ProfileScreen(
                    navController = rootNavController,
                    authViewModel = authViewModel
                )
            }

            composable("categories") {
                CategoriesScreen()
            }
            composable("add_Product") {
                AddProductScreen(
                    navController = rootNavController,
                    productViewModel = productViewModel,
                    authViewModel = authViewModel
                )
            }


            composable("orders") {
                OrdersScreen(rootNavController)
            }
        }
    }
}
