package com.example.dogsproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dogsproject.screens.DogDetailScreen
import com.example.dogsproject.screens.DogsFavouritesScreen
import com.example.dogsproject.screens.DogsListScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.DogsListScreen.route) {
        composable(route = Screen.DogsListScreen.route) {
            DogsListScreen(navController = navController)
        }
        composable(route = Screen.DogsDetailScreen.route.plus("/{breed}")) { backStackEntry ->
            backStackEntry.arguments?.getString("breed")?.let { DogDetailScreen(navController = navController, breed = it) }
        }
        composable(route = Screen.DogsFavouritesScreen.route) {
            DogsFavouritesScreen(navController = navController)
        }
    }
}