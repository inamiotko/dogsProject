package com.example.dogsproject.viewcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dogsproject.navigation.Screen

@Composable
fun DogListItem(dog: String, navController: NavController) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { navController.navigate(Screen.DogsDetailScreen.route.plus("/${dog}")) },
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = dog, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}