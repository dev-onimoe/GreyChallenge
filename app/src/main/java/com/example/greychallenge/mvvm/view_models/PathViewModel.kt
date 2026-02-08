package com.example.greychallenge.mvvm.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.greychallenge.helpers.data_helpers.DataClass
import com.example.greychallenge.mvvm.models.PathStop
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PathViewModel(
    private val container: DataClass
) : ViewModel() {

    val stops: StateFlow<List<PathStop>> = container.pathStops

    init {
        viewModelScope.launch {
            container.loadFromStorage()
        }
    }

    fun saveStops(stops: List<PathStop>) {
        viewModelScope.launch {
            container.saveToStorage(stops)
        }
    }
}

class PathViewModelFactory(
    private val container: DataClass
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PathViewModel(container) as T
    }
}
