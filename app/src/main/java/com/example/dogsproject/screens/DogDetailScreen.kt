package com.example.dogsproject.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dogsproject.viewcomponents.FavouriteFloatingButton
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogDetailsViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(
    viewModel: DogDetailsViewModel = koinViewModel(),
    navController: NavController,
    breed: String
) {

    val state by viewModel.viewState.collectAsState()
    Scaffold(topBar = {
        TopBar(breed, Modifier, iconAction = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        })
    }, floatingActionButton = {
        FavouriteFloatingButton(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            navController = navController,
        )
    }) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues = padding)
        ) {
            items(state.dogs.size) {
                GridItem(
                    dog = state.dogs[it].first, iconVisible = true
                ) { dog ->
                    viewModel.addToFav(dog)
                }
            }
        }
    }
}




