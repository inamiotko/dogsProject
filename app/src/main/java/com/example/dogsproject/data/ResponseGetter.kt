package com.example.dogsproject.data

import android.util.Log
import com.example.dogsproject.model.DogImages
import com.example.dogsproject.model.Dogs
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseGetter() {
    fun getDogBreeds(
        dogBreeds: MutableList<String>,
        _dogStateFlow: MutableStateFlow<List<String>>
    ) {
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
                } else {
                    Log.e("Http error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Dogs>, t: Throwable) {
                Log.e("Error", "${t.message}")
            }
        })
    }

    fun getDogImages(dog: String, _dogStateFlow: MutableStateFlow<List<String>>) {
        var dogBreeds: List<String> = mutableListOf()
        DogImagesService.call().getImages(dog).enqueue(object : Callback<DogImages> {
            override fun onResponse(
                call: Call<DogImages>,
                response: Response<DogImages>
            ) {
                if (response.isSuccessful) {
                    dogBreeds = response.body()?.dogImages ?: emptyList<String>()
                    _dogStateFlow.value = dogBreeds
                } else {
                    Log.e("Http error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DogImages>, t: Throwable) {
                Log.e("Error", "${t.message}")
            }
        })
    }
}