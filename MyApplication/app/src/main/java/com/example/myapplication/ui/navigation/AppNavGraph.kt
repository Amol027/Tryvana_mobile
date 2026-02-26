package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.myapplication.ui.screens.auth.*
import com.example.myapplication.ui.screens.product.ProductDetailScreen
import com.example.myapplication.ui.screens.product.AddProductScreen
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.ui.viewmodel.UserViewModel


@Composable
fun AppNavGraph(navController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()

    // ✅ Attach UserViewModel
    LaunchedEffect(Unit) {
        authViewModel.attachUserViewModel(userViewModel)
    }

    NavHost(navController, startDestination = "role") {

        composable("role") {
            RoleSelectionScreen(navController)
        }

        composable(
            route = "login/{role}",
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "user"

            LoginScreen(
                navController = navController,
                authViewModel = authViewModel,
                userViewModel = userViewModel,
                role = role
            )
        }

        composable(
            route = "register/{role}",
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "user"

            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel,
                role = role
            )
        }

        composable(
            route = "main/{role}",
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->

            val role = backStackEntry.arguments?.getString("role") ?: "user"

            MainScreen(
                rootNavController = navController,
                productViewModel = productViewModel,
                authViewModel = authViewModel,
                role = role
            )
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

        composable("add_product") {
            AddProductScreen(
                navController = navController,
                productViewModel = productViewModel,
                authViewModel = authViewModel
            )
        }
    }
}

