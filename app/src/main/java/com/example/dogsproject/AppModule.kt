package com.example.dogsproject


import com.example.dogsproject.viewmodel.DogDetailsViewModel
import com.example.dogsproject.viewmodel.DogFavoritesViewModel
import com.example.dogsproject.viewmodel.DogListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { SaveFavDogs(androidContext()) }

    viewModelOf(::DogDetailsViewModel)
    viewModelOf(::DogFavoritesViewModel)
    viewModelOf(::DogListViewModel)

}