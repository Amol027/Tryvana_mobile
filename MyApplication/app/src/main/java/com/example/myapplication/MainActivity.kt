package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.components.BottomBar
import com.example.myapplication.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // 👇 Routes where bottom bar should appear
            val bottomBarRoutes = listOf(
                "home",
                "categories",
                "orders",
                "profile"
            )

            Scaffold(
                bottomBar = {
                    if (currentRoute in bottomBarRoutes) {
                        BottomBar(navController, currentRoute)
                    }
                }
            ) { paddingValues ->

                Box(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    AppNavGraph(navController)
                }
            }
        }
    }
}
