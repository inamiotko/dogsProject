package com.example.dogsproject

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.dogsproject.viewcomponents.GridItem
import com.example.dogsproject.viewcomponents.TopBar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun DogsFavouritesScreen() {
    val ctx = LocalContext.current
    val breedNamesList = mutableListOf<String>()
    val sharedPref = ctx.getSharedPreferences("dogs", Context.MODE_PRIVATE)
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    var filteredImgList by remember { mutableStateOf(listOf<String>()) }
    var filteredNamesList by remember { mutableStateOf(listOf<String>()) }
    var breedRemembered by remember { mutableStateOf("") }
    lateinit var favouriteDogBreedsList: List<String>
    val dataStore = SaveFavDogs(ctx)
    Column {
        val dogs = dataStore.getDogs.collectAsState(initial = "").value

        if (dogs != ""){
            favouriteDogBreedsList =
                Gson().fromJson<List<String>?>(
                    dogs,
                    object : TypeToken<List<String>>() {}.type
                )
                    .reversed()
            for (element in favouriteDogBreedsList) breedNamesList.add(
                element.split("/")[4].replace(
                    "-",
                    " "
                )
            )

            filteredImgList = favouriteDogBreedsList
            filteredNamesList = breedNamesList
        }
        TopBar("Favourite dogs", Modifier, iconAction = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Favorite, "")
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
            DropdownMenu(
                expanded = dropDownMenuExpanded,
                onDismissRequest = { dropDownMenuExpanded = false }
            ) {
                breedNamesList.toSet().forEach { breed ->
                    DropdownMenuItem(
                        { Text(text = breed) },
                        onClick = {
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
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 16.dp),
            content = {
                items(filteredImgList.size) { num ->
                    GridItem(
                        breed = filteredNamesList[num],
                        img = filteredImgList[num],
                        onItemClickAction = {})
                }

            })

    }
}