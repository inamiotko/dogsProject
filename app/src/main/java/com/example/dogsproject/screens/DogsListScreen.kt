package com.example.dogsproject.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dogsproject.viewcomponents.DogListItem
import com.example.dogsproject.viewcomponents.FavouriteFloatingButton
import com.example.dogsproject.viewcomponents.TopBar
import com.example.dogsproject.viewmodel.DogListViewModel


@Composable
fun DogsListScreen(navController: NavController) {
    val viewModel: DogListViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    Column {
        TopBar("List of dog breeds", Modifier)
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (state.isEmpty()) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                }
                items(state.sortedBy { it }) { dog: String ->
                    DogListItem(dog = dog, navController)
                }
            }
            FavouriteFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 24.dp, vertical = 16.dp), navController
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DogsListScreenPreview() {
    DogsListScreen(navController = rememberNavController())
}