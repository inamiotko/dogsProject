package com.example.dogsproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogsproject.data.BreedsApiService
import com.example.dogsproject.model.Dogs
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogListViewModel : ViewModel() {
    private val _dogStateFlow = MutableStateFlow(emptyList<String>())
    val dogStateFlow: StateFlow<List<String>>
        get() = _dogStateFlow
    val dogBreeds: MutableList<String> = mutableListOf()

    init {
        viewModelScope.launch {
            BreedsApiService.call().getDogs().enqueue(object : Callback<Dogs> {
                override fun onResponse(call: Call<Dogs>, response: Response<Dogs>) {
                    if (response.isSuccessful) {
                        var map: Map<String, List<String>> = HashMap()
                        map = Gson().fromJson(response.body()?.dogBreeds, map.javaClass)
                        for ((k, v) in map) {
                            if (v.isNotEmpty()) {
                                for (subBreed in v) {
                                    dogBreeds.add("$k $subBreed")
                                }
                            } else {
                                k.let { dogBreeds.add(k) }
                            }
                        }
                        _dogStateFlow.value = dogBreeds
                    }
                }

                override fun onFailure(call: Call<Dogs>, t: Throwable) {
                    Log.e("Error", "${t.message}")
                }
            })
        }
    }
}
