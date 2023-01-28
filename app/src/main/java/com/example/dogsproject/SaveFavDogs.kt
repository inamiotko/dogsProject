package com.example.dogsproject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SaveFavDogs(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("favDogs")
        val DOGS_KEY = stringPreferencesKey("fav_dogs")
    }

    val getDogs: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[DOGS_KEY] ?: ""
        }

    suspend fun saveDogs(listOfDogs: String) {
        context.dataStore.edit { preferences ->
            preferences[DOGS_KEY] = listOfDogs
        }
    }
}