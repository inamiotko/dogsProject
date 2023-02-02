package com.example.dogsproject.viewcomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FavouriteIcon(
    modifier: Modifier,
    color: Color = Color.White,
    isFavourite: Boolean
) {
    Icon(
        modifier = modifier,
        tint = color,
        imageVector = if (isFavourite) {
            Icons.Filled.Favorite
        } else {
            Icons.Default.FavoriteBorder
        },
        contentDescription = null
    )
}
