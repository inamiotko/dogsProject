package com.example.dogsproject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SaveDogManager(private val context: Context) {
    private val gson = Gson()

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("favDogs")
        val DOGS_KEY = stringPreferencesKey("fav_dogs")
    }

    val getDogs: Flow<List<Dog>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[DOGS_KEY] ?: "[]"
            val type = object : TypeToken<List<Dog>>() {}.type
            gson.fromJson(json, type)
        }
    private suspend fun saveDogs(listOfDogs: List<Dog>) {
        val json = gson.toJson(listOfDogs)
        context.dataStore.edit { preferences ->
            preferences[DOGS_KEY] = json
        }
    }

    suspend fun addDog(dog: Dog) {
        val currentDogs = getDogs.firstOrNull() ?: emptyList()
        val updatedDogs = currentDogs + dog
        saveDogs(updatedDogs)
    }

    suspend fun removeDog(dogId: Int) {
        val currentDogs = getDogs.firstOrNull() ?: emptyList()
        val updatedDogs = currentDogs.filter { it.id != dogId }
        saveDogs(updatedDogs)
    }

    suspend fun isFavoriteDog(dog: Dog): Boolean {
        val currentDogs = getDogs.firstOrNull() ?: emptyList()
        return currentDogs.contains(dog)

    }
}


data class Dog(
    val id: Int,
    val url: String,
    val breed: String
)