package com.example.dogsproject.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.Dog
import com.example.dogsproject.SaveDogManager
import com.example.dogsproject.additional.splitToGetBreedName
import com.example.dogsproject.data.ResponseGetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val saveDogManager: SaveDogManager,
    private val response: ResponseGetter) : ViewModel() {
    private val _viewState = MutableStateFlow(DogDetailsViewState(emptyList()))
    val viewState: StateFlow<DogDetailsViewState>
        get() = _viewState
    private var dog = savedStateHandle.get<String>("breed") ?: ""

    init {
        viewModelScope.launch {
            if (dog.contains(" ")) dog = dog.splitToGetBreedName()
            _viewState.value = DogDetailsViewState(
                response.getDogImages(dog).map { Pair(it, saveDogManager.isFavoriteDog(it)) })
        }
    }

    fun addToFav(dog: Dog) {
        viewModelScope.launch {
            if(!saveDogManager.isFavoriteDog(dog)) saveDogManager.addDog(dog)
            else saveDogManager.removeDog(dog.id)
        }
    }
}

data class DogDetailsViewState(
    val dogs: List<Pair<Dog, Boolean>>
)