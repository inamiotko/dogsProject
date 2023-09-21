package com.example.dogsproject.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dogsproject.SaveFavDogs
import com.example.dogsproject.additional.intoListOfString
import com.example.dogsproject.viewcomponents.FavouriteFloatingButton
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogDetailsViewModel


@Composable
fun DogDetailScreen(navController: NavController, breed: String) {
    var dogsFav by remember { mutableStateOf(listOf<String>()) }
    val ctx = LocalContext.current
    val viewModel: DogDetailsViewModel = viewModel()
    val state by viewModel.dogStateFlow.collectAsState()
    val scope = rememberCoroutineScope()
    val dataStore = SaveFavDogs(ctx)
    val dogs = dataStore.getDogs.collectAsState(initial = "").value
    var favouriteDogBreedsList: List<String> = dogs.intoListOfString()
    ConstraintLayout(Modifier.fillMaxHeight()) {
        val (box, floatingButton, topBar) = createRefs()
        TopBar(breed, Modifier.constrainAs(topBar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        })
        Box(Modifier.constrainAs(box) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(topBar.bottom)
        }) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp)
            ) {
                items(state.size) { num ->
                    GridItem(
                        breed = "", img = state[num], iconVisible = true
                    ) {
                        if (!favouriteDogBreedsList.contains(state[num])) {
                            favouriteDogBreedsList = favouriteDogBreedsList + state[num]
                            Toast.makeText(
                                ctx, "Image saved to favourites", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            favouriteDogBreedsList = favouriteDogBreedsList - state[num]
                            Toast.makeText(
                                ctx, "Image removed from favourites", Toast.LENGTH_SHORT
                            ).show()
                        }
                        dataStore.saveToSharedPrefs(favouriteDogBreedsList, scope)
                    }
                }
            }
        }
        FavouriteFloatingButton(
            modifier = Modifier
                .constrainAs(floatingButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            navController = navController,
        )
    }
}



