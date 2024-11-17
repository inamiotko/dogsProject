package com.example.dogsproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.data.ResponseGetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogListViewModel(
    private val response: ResponseGetter
) : ViewModel() {

    private val _dogStateFlow = MutableStateFlow(emptyList<String>())
    val dogStateFlow: StateFlow<List<String>>
        get() = _dogStateFlow

    init {
        viewModelScope.launch {
            _dogStateFlow.value = response.getDogBreeds()
        }
    }
}
