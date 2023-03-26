package com.larsorbegozo.qlock.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.larsorbegozo.qlock.data.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClockViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = DataStoreManager(application)

    val getTheme = dataStore.getTheme().asLiveData(Dispatchers.IO)

    val getSelectedItem = dataStore.getSelectedItem().asLiveData(Dispatchers.IO)

    var getSelectedThemeIndex: Int = 0

    var isReady = false

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setTheme(isDarkMode)
        }
    }

    fun setSelectedTheme(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setSelectedItem(value)
        }
    }
}