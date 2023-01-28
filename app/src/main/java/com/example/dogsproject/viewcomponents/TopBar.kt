package com.example.dogsproject.viewcomponents

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, modifier: Modifier, iconAction: @Composable () -> Unit = {}, actions: @Composable (RowScope.() -> Unit) = {} ) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = iconAction,
        actions = actions
    )
}
