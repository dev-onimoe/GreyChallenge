package com.example.greychallenge.helpers.data_helpers

import com.example.greychallenge.mvvm.models.PathStop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

class DataClass (
    private val storage: PathLocalDataSource
) {
    private val _pathStops = MutableStateFlow<List<PathStop>>(emptyList())

    val pathStops: StateFlow<List<PathStop>> = _pathStops

    suspend fun loadFromStorage() {
        val stored = storage.loadPathStops(key = "path_stops")
        println("Loaded ${stored.size} stops from storage")
        _pathStops.value = stored
    }

    suspend fun saveToStorage(stops: List<PathStop>) {
        storage.savePathStops(key = "path_stops", stops)
        _pathStops.value = stops
    }
}