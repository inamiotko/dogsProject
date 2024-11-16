package com.example.dogsproject.viewcomponents

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dogsproject.navigation.Screen

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

@Composable
@Preview(showBackground = true)
fun PreviewFavouriteFloatingButton() {
    FavouriteFloatingButton(
        modifier = Modifier,
        navController = NavController(LocalContext.current)
    )
}