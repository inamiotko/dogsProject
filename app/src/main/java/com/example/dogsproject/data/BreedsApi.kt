package com.example.dogsproject.data

import com.example.dogsproject.model.Dogs
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BreedsApi {
    @GET("list/all")
    fun getDogs(): Call<Dogs>
}

object BreedsApiService {
    private const val BREEDS_URL = "https://dog.ceo/api/breeds/"
    fun call(): BreedsApi =
        Retrofit.Builder()
            .baseUrl(BREEDS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BreedsApi::class.java)
}