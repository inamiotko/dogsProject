package com.example.dogsproject.viewcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun GridItem(breed: String, img: String, onItemClickAction: (img: String) -> Unit) {
    Card(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Transparent),
                onClick = { onItemClickAction(img) }
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
                        .data(img).build()
                ),
                contentDescription = null,

                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        if(breed != "") {
            Text(
                text = breed, modifier = Modifier
                    .padding(8.dp),
                color = Color.Black
            )
        }
    }
}
