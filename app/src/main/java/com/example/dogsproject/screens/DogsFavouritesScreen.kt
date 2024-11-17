package com.example.dogsproject.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dogsproject.R
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogFavoritesViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsFavouritesScreen(
    viewModel: DogFavoritesViewModel = koinViewModel(),
    navController: NavController
) {
    val state = viewModel.viewState.collectAsState().value
    var snackBarVisible by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    var dropDownMenuExpanded by remember { mutableStateOf(false) }


    Scaffold(snackbarHost = { if (snackBarVisible) SnackbarHost(snackBarHostState) }, topBar = {
        TopBar("Favourite dogs", Modifier, iconAction = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        }) {
            IconButton(onClick = { dropDownMenuExpanded = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = ""
                )
            }
            DropdownMenu(expanded = dropDownMenuExpanded,
                onDismissRequest = { dropDownMenuExpanded = false }) {
                state.dogs.map { it.breed }.toSet().forEach { breed ->
                    DropdownMenuItem({ Text(text = breed) }, onClick = {
                        dropDownMenuExpanded = false
                        viewModel.filterByBreed(breed = breed)
                    })
                }
            }
            IconButton(onClick = { viewModel.resetFiltering() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_restart),
                    contentDescription = ""
                )
            }

        }
    }) { scaffoldPadding ->
        Column(
            Modifier
                .clickable { snackBarVisible = false }
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            if (state.filteredDogs.isNotEmpty())
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
                    content = {
                        items(state.filteredDogs.size) { num ->
                            GridItem(
                                dog = state.filteredDogs[num],
                                iconVisible = false,
                                onItemClickAction = { it ->
                                    viewModel.removeFromFav(it)
                                })
                        }

                    })
            else {
                Text(
                    "You need to pick some favourites first :)",
                    Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
        }
    }
}


