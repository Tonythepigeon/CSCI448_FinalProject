package com.csci448.pathmapper.ui.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csci448.pathmapper.ui.screens.*

@Composable
fun PathMapperNavHost(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home_screen"
    ) {
        composable("home_screen") {
            HomeScreen(navController = navController)
        }
        composable("options_screen") {
            OptionsScreen(navController = navController)
        }
        composable("past_routes_screen") {
            PastRoutesScreen(navController = navController)
        }
        composable("route_details_screen") {
            RouteDetailsScreen(navController = navController)
        }
        composable("routing_screen") {
            RoutingScreen(navController = navController)
        }
    }
}