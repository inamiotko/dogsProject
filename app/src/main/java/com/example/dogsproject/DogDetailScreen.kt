package com.example.dogsproject

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dogsproject.viewcomponents.FavouriteFloatingButton
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogDetailsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DogDetailScreen(navController: NavController, breed: String) {
    var dogsFav by remember { mutableStateOf(listOf<String>()) }
    val ctx = LocalContext.current
    val viewModel: DogDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val dataStore = SaveFavDogs(ctx)
    Column {
        val dogs = dataStore.getDogs.collectAsState(initial = "").value
        TopBar(breed, Modifier)
        Box {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp)
            ) {
                items(state.size) { num ->
                    GridItem(
                        breed = "",
                        img = state[num]
                    ) {
                        Toast.makeText(ctx, "Image saved to favourites", Toast.LENGTH_SHORT)
                            .show()
                        if (!dogsFav.contains(state[num]))
                            dogsFav = dogsFav + state[num]
                        if (dogs == "") {
                            saveToSharedPrefs(dogsFav, scope, dataStore)
                        } else {
                            var favouriteDogBreedsList = Gson().fromJson<List<String>?>(
                                dogs,
                                object : TypeToken<List<String>>() {}.type
                            )
                            for (favBreed in dogsFav)
                                if (!favouriteDogBreedsList.contains(favBreed))
                                    favouriteDogBreedsList = favouriteDogBreedsList + favBreed
                            saveToSharedPrefs(favouriteDogBreedsList, scope, dataStore)
                        }
                    }
                }
            }
            FavouriteFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                navController = navController,
                dogsFav
            )
        }
    }
}


fun saveToSharedPrefs(data: List<String>, scope: CoroutineScope, dataStore: SaveFavDogs) {
    val toBeSaved = Gson().toJson(data)
    scope.launch {
        dataStore.saveDogs(toBeSaved)
    }
}


