package com.example.dogsproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.SaveFavDogs
import com.example.dogsproject.additional.intoListOfString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DogFavoritesViewModel(private val dataStore: SaveFavDogs) : ViewModel() {
    private val _viewState = MutableStateFlow(DogFavoritesViewState(emptyList()))
    val viewState = _viewState

    init {
        getFavDogs()
    }

    private fun getFavDogs() {
        viewModelScope.launch {
            dataStore.getDogs.collect {
                _viewState.value =
                    _viewState.value.copy(favoriteDogs = it.intoListOfString().reversed())
            }
        }
    }

    fun saveToFav(dogs: List<String>) {
        dataStore.saveToSharedPrefs(
            dogs.reversed(), viewModelScope
        )
    }

    data class DogFavoritesViewState(val favoriteDogs: List<String>)
}