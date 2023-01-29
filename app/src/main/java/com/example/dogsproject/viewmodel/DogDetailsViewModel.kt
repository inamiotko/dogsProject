package com.example.dogsproject.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.data.DogImagesService
import com.example.dogsproject.model.DogImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dogStateFlow = MutableStateFlow(emptyList<String>())
    val dogStateFlow: StateFlow<List<String>>
        get() = _dogStateFlow
    var dogBreeds: List<String> = mutableListOf()
    private var dog = savedStateHandle.get<String>("breed") ?: ""

    init {
        viewModelScope.launch {
            if (dog.contains(" ")) dog = dog.split(" ")[0]
            DogImagesService.call().getImages(dog).enqueue(object : Callback<DogImages> {
                override fun onResponse(
                    call: Call<DogImages>,
                    response: Response<DogImages>
                ) {
                    if (response.isSuccessful) {
                        dogBreeds = response.body()?.dogImages ?: emptyList<String>()
                        _dogStateFlow.value = dogBreeds
                    }
                }

                override fun onFailure(call: Call<DogImages>, t: Throwable) {
                    Log.e("Error", "${t.message}")
                }
            })
        }
    }
}
