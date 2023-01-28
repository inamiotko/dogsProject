package com.example.dogsproject.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Dogs(@SerializedName("message") val dogBreeds: JsonObject)

data class DogImages(@SerializedName("message") val dogImages: List<String>)


