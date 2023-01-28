package com.example.dogsproject.viewcomponents

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dogsproject.navigation.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun FavouriteFloatingButton(
    modifier: Modifier,
    navController: NavController,
    favBreeds: List<String> = emptyList()
) {
    val ctx = LocalContext.current
    FloatingActionButton(
        onClick = {
//            val sharedPref = ctx.getSharedPreferences("test", Context.MODE_PRIVATE)
//            val dogsList = sharedPref.getString("favDogBreeds", "")?: mutableListOf<String>("").toString()
//
//            var alreadySaved = Gson().fromJson<List<String>?>(
//                dogsList,
//                object : TypeToken<List<String>>() {}.type
//            )
//            for (breed in favBreeds) if (!alreadySaved.isNullOrEmpty() && !alreadySaved.contains(breed))
//                alreadySaved = alreadySaved + breed
//            val toBeSaved = Gson().toJson(alreadySaved)
//            with(sharedPref.edit()) {
//                putString("favDogBreeds", toBeSaved)
//                apply()
//            }
            navController.navigate(Screen.DogsFavouritesScreen.route)
        },
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
        contentColor = Color.Gray,
        containerColor = Color.White,
        modifier = modifier
    ) {
        Icon(Icons.Filled.Favorite, "")
    }
}