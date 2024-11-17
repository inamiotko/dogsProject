package com.example.dogsproject.data

import com.example.dogsproject.Dog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ResponseGetter {

    suspend fun getDogBreeds(): List<String> = withContext(Dispatchers.IO) {
        val response = BreedsApiService.call().getDogs().awaitResponse()
        if (response.isSuccessful) {
            val mapType = object : TypeToken<Map<String, List<String>>>() {}.type
            val map: Map<String, List<String>> =
                Gson().fromJson(response.body()?.dogBreeds, mapType)
            map.flatMap { (breed, subBreeds) ->
                if (subBreeds.isNotEmpty()) subBreeds.map { "$breed $it" } else listOf(breed)
            }
        } else {
            throw Exception("Failed to fetch breeds: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getDogImages(dog: String): List<Dog> = withContext(Dispatchers.IO) {
        val response = DogImagesService.call().getImages(dog).awaitResponse()
        if (response.isSuccessful) {
            response.body()?.dogImages?.mapIndexed { index, imageUrl ->
                Dog(index, imageUrl)
            } ?: emptyList()
        } else {
            throw Exception("Failed to fetch dog images: ${response.errorBody()?.string()}")
        }
    }
}
