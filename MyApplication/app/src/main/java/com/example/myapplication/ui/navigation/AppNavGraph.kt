package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.auth.LoginScreen
import com.example.myapplication.ui.screens.auth.RegisterScreen
import com.example.myapplication.ui.screens.home.*
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.ui.viewmodel.UserViewModel
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication.ui.screens.product.ProductDetailScreen


@Composable
fun AppNavGraph(navController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel,
                userViewModel = userViewModel
            )
        }

        composable("register") {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                userViewModel = userViewModel,
                productViewModel = productViewModel
            )
        }

        // ✅ FIXED HERE
        composable("profile") {
            ProfileScreen(
                navController = navController,
                userViewModel = userViewModel,
                authViewModel = authViewModel
            )
        }

        composable("categories") {
            CategoriesScreen()
        }

        composable("orders") {
            OrdersScreen(navController)
        }
        composable(
            route = "detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                navController = navController,
                productViewModel = productViewModel,
                productId = productId
            )
        }

    }
}
