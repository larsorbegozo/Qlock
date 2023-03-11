package com.larsorbegozo.qlock.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.larsorbegozo.qlock.data.ClockDao

class ClockViewModel(private val clockDao: ClockDao) : ViewModel() {

}

class ClockViewModelFactory(private val clockDao: ClockDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClockViewModel(clockDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}