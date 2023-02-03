package com.example.dogsproject.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.additional.splitToGetBreedName
import com.example.dogsproject.data.ResponseGetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dogStateFlow = MutableStateFlow(emptyList<String>())
    val dogStateFlow: StateFlow<List<String>>
        get() = _dogStateFlow
    private var dog = savedStateHandle.get<String>("breed") ?: ""
    private val response = ResponseGetter()

    init {
        viewModelScope.launch {
            if (dog.contains(" ")) dog = dog.splitToGetBreedName()
            response.getDogImages(dog, _dogStateFlow)
        }
    }
}
