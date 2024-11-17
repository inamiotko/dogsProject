package com.example.dogsproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.Dog
import com.example.dogsproject.SaveDogManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DogFavoritesViewModel(private val dataStore: SaveDogManager) : ViewModel() {
    private val _viewState = MutableStateFlow(DogFavoritesViewState(emptyList(), emptyList()))
    val viewState = _viewState

    init {
        getFavDogs()
    }

    private fun getFavDogs() {
        viewModelScope.launch {
            dataStore.getDogs.collect {
                _viewState.value =
                    _viewState.value.copy(dogs = it.reversed(), filteredDogs = it.reversed())
            }
        }
    }

    fun filterByBreed(breed: String) {
        val dogs = _viewState.value.dogs
        _viewState.value =
            _viewState.value.copy(filteredDogs = dogs.filter { it.breed == breed })
    }

    fun resetFiltering() {
        _viewState.value =
            _viewState.value.copy(filteredDogs = _viewState.value.dogs)
    }

    fun removeFromFav(dog: Dog) {
        viewModelScope.launch {
            dataStore.removeDog(dog.id)
        }
    }

    data class DogFavoritesViewState(val dogs: List<Dog>, val filteredDogs: List<Dog>)
}