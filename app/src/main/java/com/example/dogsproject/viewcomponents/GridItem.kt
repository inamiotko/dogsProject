package com.example.dogsproject.viewcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.dogsproject.Dog


@Composable
fun GridItem(
    dog: Dog,
    iconVisible: Boolean,
    onItemClickAction: (dog: Dog) -> Unit
) {
    var isFavourite by remember { mutableStateOf(false) }
    isFavourite =false
    Card(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Transparent),
                onClick = {
                    isFavourite = !isFavourite
                    onItemClickAction(dog)
                }
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current).crossfade(true)
                        .data(dog.url).build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (iconVisible) FavouriteIcon(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp), isFavourite = isFavourite
            )
        }
        if (dog.url != "") {
            Text(
                text = dog.url, modifier = Modifier
                    .padding(8.dp),
                color = Color.Black
            )
        }
    }
}