package com.example.dogsproject.data

import com.example.dogsproject.model.DogImages
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DogImageApi {
    @GET("{dog}/images")
    fun getImages(@Path("dog") dog: String): Call<DogImages>
}

object DogImagesService {
    private const val IMAGES_URL = "https://dog.ceo/api/breed/"
    fun call(): DogImageApi =
        Retrofit.Builder()
            .baseUrl(IMAGES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogImageApi::class.java)
}
