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
import com.example.dogsproject.additional.replaceSpaceWithDash
import com.example.dogsproject.additional.splitToGetBreed
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogFavoritesViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsFavouritesScreen(viewModel: DogFavoritesViewModel = koinViewModel(), navController: NavController) {
    val dogs = viewModel.viewState.collectAsState().value.dogs
    val breedNamesList = mutableListOf<String>()
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    var filterOn by remember { mutableStateOf(false) }
    var filteredImgList by remember { mutableStateOf(listOf<String>()) }
    var filteredNamesList by remember { mutableStateOf(listOf<String>()) }
    var breedRemembered by remember { mutableStateOf("") }
    var snackBarVisible by remember { mutableStateOf(false) }
    val favouriteDogBreedsList: List<String> = mutableListOf()
    val snackBarHostState = remember { SnackbarHostState() }


    Scaffold(snackbarHost = { if (snackBarVisible) SnackbarHost(snackBarHostState) }, topBar = {
        TopBar("Favourite dogs", Modifier, iconAction = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        }) {
            IconButton(onClick = { dropDownMenuExpanded = !dropDownMenuExpanded }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = ""
                )
            }
            IconButton(onClick = {
                filteredImgList = favouriteDogBreedsList
                filteredNamesList = breedNamesList
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_restart),
                    contentDescription = ""
                )
            }
            DropdownMenu(expanded = dropDownMenuExpanded,
                onDismissRequest = { dropDownMenuExpanded = false }) {
                breedNamesList.toSet().forEach { breed ->
                    DropdownMenuItem({ Text(text = breed) }, onClick = {
                        filterOn = !filterOn
                        dropDownMenuExpanded = false
                        breedRemembered =
                            if (breed.contains(" ")) breed.replaceSpaceWithDash() else breed
                        filteredImgList = favouriteDogBreedsList.filter {
                            it.splitToGetBreed() == breedRemembered
                        }
                        filteredNamesList = breedNamesList.filter { it == breed }
                    })
                }
            }
        }
    }) { scaffoldPadding ->
        Column(
            Modifier
                .clickable { snackBarVisible = false }
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            filterOn = false
            if (dogs.isNotEmpty())
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
                    content = {
                        items(dogs.size) { num ->
                            GridItem(
                                dog = dogs[num],
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


//            LaunchedEffect(Unit) {
//                localCoroutineScope.launch {
//                    val result = snackBarHostState.showSnackbar(
//                        message = "Image removed from favourites!",
//                        duration = SnackbarDuration.Short
//                    )
//                    snackBarVisible = when (result) {
//                        SnackbarResult.Dismissed -> false
//                        else -> true
//                    }
//                }
//            }
        }
    }
}


