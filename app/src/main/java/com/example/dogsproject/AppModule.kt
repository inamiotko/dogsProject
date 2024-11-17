package com.example.dogsproject


import com.example.dogsproject.data.ResponseGetter
import com.example.dogsproject.viewmodel.DogDetailsViewModel
import com.example.dogsproject.viewmodel.DogFavoritesViewModel
import com.example.dogsproject.viewmodel.DogListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { ResponseGetter() }
    single { SaveDogManager(androidContext()) }
    viewModel { DogListViewModel(get()) }

    viewModel { DogDetailsViewModel(get(), get(), get()) }
    viewModel { DogFavoritesViewModel(get()) }

}