package com.example.dogsproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.data.ResponseGetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {
    private val _dogStateFlow = MutableStateFlow(emptyList<String>())
    val dogStateFlow: StateFlow<List<String>>
        get() = _dogStateFlow
    private val dogBreeds: MutableList<String> = mutableListOf()
    private val response = ResponseGetter()
    init {
        viewModelScope.launch {
            response.getDogBreeds(dogBreeds, _dogStateFlow)
        }
    }
}
