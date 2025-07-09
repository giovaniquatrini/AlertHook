
package com.example.alerthook

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("app_selection") {
            AppSelectionScreen(navController = navController)
        }
        composable("webhook_config") {
            WebhookConfigScreen(navController = navController)
        }
    }
}
