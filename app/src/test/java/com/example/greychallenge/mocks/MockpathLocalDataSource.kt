package com.example.greychallenge.mocks

import com.example.greychallenge.helpers.data_helpers.PathLocalDataSource


import com.example.greychallenge.mvvm.models.PathStop

class MockPathLocalDataSource : PathLocalDataSource {

    private val storage = mutableMapOf<String, MutableList<PathStop>>()

    override suspend fun loadPathStops(key: String): MutableList<PathStop> {
        return storage[key]?.toMutableList() ?: mutableListOf()
    }

    override suspend fun savePathStops(key: String, stops: List<PathStop>) {
        storage[key] = stops.toMutableList()
    }
}
