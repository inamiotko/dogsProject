package com.example.dogsproject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SaveFavDogs(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("favDogs")
        val DOGS_KEY = stringPreferencesKey("fav_dogs")
    }

    val getDogs: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[DOGS_KEY] ?: ""
        }

    private suspend fun saveDogs(listOfDogs: String) {
        context.dataStore.edit { preferences ->
            preferences[DOGS_KEY] = listOfDogs
        }
    }

    fun saveToSharedPrefs(data: List<String>, scope: CoroutineScope, dataStore: SaveFavDogs) {
        val toBeSaved = Gson().toJson(data)
        scope.launch {
            dataStore.saveDogs(toBeSaved)
        }
    }
}