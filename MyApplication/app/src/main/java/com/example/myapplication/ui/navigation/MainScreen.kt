package com.example.myapplication.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myapplication.ui.components.BottomBar
import com.example.myapplication.ui.screens.home.CategoriesScreen
import com.example.myapplication.ui.screens.home.HomeContent
import com.example.myapplication.ui.screens.home.OrdersScreen
import com.example.myapplication.ui.screens.home.ProfileScreen
import com.example.myapplication.ui.screens.product.AddProductScreen
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.ProductViewModel

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
                bottomNavController = bottomNavController,
                currentRoute = currentRoute,
                role = role
            )

        }

    ) { paddingValues ->

        NavHost(

            navController = bottomNavController,

            startDestination =
                if (role.lowercase() == "seller")
                    "seller_home"
                else
                    "user_home",

            modifier = Modifier.padding(paddingValues)

        ) {

            // ================= USER HOME =================

            composable("user_home") {

                LaunchedEffect(Unit) {
                    productViewModel.loadProducts()
                }

                HomeContent(
                    rootNavController = rootNavController,
                    productViewModel = productViewModel
                )
            }

            // ================= SELLER HOME =================

            composable("seller_home") {

                LaunchedEffect(token) {
                    if (token.isNotEmpty()) {
                        productViewModel.loadSellerProducts(token)
                    }
                }

                HomeContent(
                    rootNavController = rootNavController,
                    productViewModel = productViewModel
                )
            }

            // ================= PROFILE =================

            composable("profile") {

                ProfileScreen(
                    navController = rootNavController,
                    authViewModel = authViewModel
                )

            }

            // ================= CATEGORIES =================

            composable("categories") {

                CategoriesScreen(bottomNavController)

            }

            // ================= ADD PRODUCT =================

            composable("add_product") {

                AddProductScreen(
                    navController = rootNavController,
                    productViewModel = productViewModel,
                    authViewModel = authViewModel
                )

            }

            // ================= ORDERS =================

            composable("orders") {

                OrdersScreen(rootNavController)

            }
        }
    }
}
