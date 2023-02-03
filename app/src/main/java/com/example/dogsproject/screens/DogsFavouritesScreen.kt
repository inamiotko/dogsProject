package com.example.dogsproject.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dogsproject.R
import com.example.dogsproject.SaveFavDogs
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsFavouritesScreen() {
    val ctx = LocalContext.current
    val breedNamesList = mutableListOf<String>()
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    var filterOn by remember { mutableStateOf(false) }
    var filteredImgList by remember { mutableStateOf(listOf<String>()) }
    var filteredNamesList by remember { mutableStateOf(listOf<String>()) }
    var breedRemembered by remember { mutableStateOf("") }
    var snackBarVisible by remember { mutableStateOf(false) }
    var hideSnackBar by remember { mutableStateOf(true) }
    var favouriteDogBreedsList: List<String> = mutableListOf()
    val scope = rememberCoroutineScope()
    val dataStore = SaveFavDogs(ctx)
    val snackBarHostState = remember { SnackbarHostState() }
    val localCoroutineScope = rememberCoroutineScope()


    Column(Modifier.clickable { snackBarVisible = false }) {
        filterOn = false
        val dogs = dataStore.getDogs.collectAsState(initial = "").value
        TopBar("Favourite dogs", Modifier, iconAction = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Favorite, "")
            }
        }) {
            IconButton(onClick = { dropDownMenuExpanded = !dropDownMenuExpanded }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter), contentDescription = ""
                )
            }
            IconButton(onClick = {
                filteredImgList = favouriteDogBreedsList
                filteredNamesList = breedNamesList
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_restart), contentDescription = ""
                )
            }
            DropdownMenu(expanded = dropDownMenuExpanded,
                onDismissRequest = { dropDownMenuExpanded = false }) {
                breedNamesList.toSet().forEach { breed ->
                    DropdownMenuItem({ Text(text = breed) }, onClick = {
                        filterOn = !filterOn
                        dropDownMenuExpanded = false
                        breedRemembered =
                            if (breed.contains(" ")) breed.replace(" ", "-") else breed
                        filteredImgList = favouriteDogBreedsList.filter {
                            it.split("/")[4] == breedRemembered
                        }
                        filteredNamesList = breedNamesList.filter { it == breed }
                    })
                }
            }
        }
        if (dogs != "") {
            favouriteDogBreedsList = Gson().fromJson<List<String>?>(
                dogs, object : TypeToken<List<String>>() {}.type
            ).reversed()
            for (element in favouriteDogBreedsList) breedNamesList.add(
                element.split("/")[4].replace(
                    "-", " "
                )
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
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
            content = {
                items(filteredImgList.size) { num ->
                    GridItem(breed = filteredNamesList[num],
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
                            dataStore.saveToSharedPrefs(
                                favouriteDogBreedsList.reversed(), scope
                            )
                        })
                }

            })
        Spacer(modifier = Modifier.weight(1f))
        if (snackBarVisible) {
            Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { scaffoldPadding ->
                Box(
                    modifier = Modifier
                        .padding(scaffoldPadding)
                        .fillMaxSize()
                ) {}
            }
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
