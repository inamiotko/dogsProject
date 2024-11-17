package com.example.dogsproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.Dog
import com.example.dogsproject.SaveDogManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DogFavoritesViewModel(private val dataStore: SaveDogManager) : ViewModel() {
    private val _viewState = MutableStateFlow(DogFavoritesViewState(emptyList()))
    val viewState = _viewState

    init {
        getFavDogs()
    }

    private fun getFavDogs() {
        viewModelScope.launch {
            dataStore.getDogs.collect {
                _viewState.value =
                    _viewState.value.copy(dogs = it.reversed())
            }
        }
    }

    fun saveToFav(dog: Dog) {
        viewModelScope.launch {
            dataStore.addDog(dog)
        }
    }

    fun removeFromFav(dog: Dog) {
        viewModelScope.launch {
            dataStore.removeDog(dog.id)
        }
    }

    data class DogFavoritesViewState(val dogs: List<Dog>)
}