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
) {
    FloatingActionButton(
        onClick = {
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