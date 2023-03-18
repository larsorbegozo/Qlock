package com.larsorbegozo.qlock.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_PREFERENCES")

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val darkModeKey = booleanPreferencesKey("DARK_MODE_KEY")
        val selectedItem = intPreferencesKey("SELECTED_ITEM")
    }

    suspend fun setTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkModeKey] = isDarkMode
        }
    }

    fun getTheme(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val uiMode = preferences[darkModeKey] ?: false
                uiMode
            }
    }

    suspend fun setSelectedItem(value: Int) {
        dataStore.edit { preferences ->
            preferences[selectedItem] = value
        }
    }

    fun getSelectedItem(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
            val selectedItem = preferences[selectedItem] ?: 0
            selectedItem
            }
    }
}