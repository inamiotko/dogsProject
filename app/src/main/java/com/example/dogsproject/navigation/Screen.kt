package com.example.dogsproject.navigation

sealed class Screen(val route: String){
    object DogsListScreen : Screen("dogs_list_screen")
    object DogsDetailScreen : Screen("dogs_detail_screen")
    object DogsFavouritesScreen : Screen("dogs_favourites_screen")
}