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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogsproject.R
import com.example.dogsproject.additional.replaceDashWithSpace
import com.example.dogsproject.additional.replaceSpaceWithDash
import com.example.dogsproject.additional.splitToGetBreed
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogFavoritesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsFavouritesScreen(viewModel: DogFavoritesViewModel = koinViewModel()) {
    val dogs = viewModel.viewState.collectAsState().value.favoriteDogs
    val breedNamesList = mutableListOf<String>()
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    var filterOn by remember { mutableStateOf(false) }
    var filteredImgList by remember { mutableStateOf(listOf<String>()) }
    var filteredNamesList by remember { mutableStateOf(listOf<String>()) }
    var breedRemembered by remember { mutableStateOf("") }
    var snackBarVisible by remember { mutableStateOf(false) }
    var favouriteDogBreedsList: List<String> = mutableListOf()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val localCoroutineScope = rememberCoroutineScope()


    Scaffold(snackbarHost = { if (snackBarVisible) SnackbarHost(snackBarHostState) }, topBar = {
        TopBar("Favourite dogs", Modifier, iconAction = {
            IconButton(onClick = {}) {
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

            if (dogs.isNotEmpty()) {
                for (element in favouriteDogBreedsList) breedNamesList.add(
                    element.splitToGetBreed().replaceDashWithSpace()
                )
                if (!filterOn && filteredImgList.isEmpty()) {
                    filteredImgList = favouriteDogBreedsList
                    filteredNamesList = breedNamesList
                }
            } else {
                Text(
                    "You need to pick some favourites first :)",
                    Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
                content = {
                    items(filteredImgList.size) { num ->
                        GridItem(
                            breed = filteredNamesList[num],
                            img = filteredImgList[num],
                            iconVisible = false,
                            onItemClickAction = {
                                snackBarVisible = true
                                filterOn = false
                                if (favouriteDogBreedsList.indexOf(filteredImgList[num]) != -1) favouriteDogBreedsList =
                                    favouriteDogBreedsList - favouriteDogBreedsList[favouriteDogBreedsList.indexOf(
                                        filteredImgList[num]
                                    )]
                                filteredImgList = filteredImgList - filteredImgList[num]
                            filteredNamesList = filteredNamesList - filteredNamesList[num]
                            viewModel.saveToFav(favouriteDogBreedsList)
                        })
                }

            })
            LaunchedEffect(Unit) {
                localCoroutineScope.launch {
                    val result = snackBarHostState.showSnackbar(
                        message = "Image removed from favourites!",
                        duration = SnackbarDuration.Short
                    )
                    snackBarVisible = when (result) {
                        SnackbarResult.Dismissed -> false
                        else -> true
                    }
                }
            }
        }
    }
}

