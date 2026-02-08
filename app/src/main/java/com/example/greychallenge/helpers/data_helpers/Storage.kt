package com.example.greychallenge.helpers.data_helpers

import android.content.Context
import com.example.greychallenge.mvvm.models.PathStop
import com.google.gson.Gson
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken

interface PathLocalDataSource {

    suspend fun loadPathStops(key: String): MutableList<PathStop>

    suspend fun savePathStops(key: String, stops: List<PathStop>)
}


class PathLocalDataSourceImpl(
    context: Context
) : PathLocalDataSource {

    private val prefs = context.getSharedPreferences(
        "path_storage",
        Context.MODE_PRIVATE
    )

    private val gson = Gson()
    private var cache: List<PathStop> = emptyList()

    override suspend fun loadPathStops(key: String): MutableList<PathStop> {
        val json = prefs.getString(key, null) ?: return fallbackStops

        val type = object : TypeToken<List<PathStop>>() {}.type
        val stops = gson.fromJson<List<PathStop>>(json, type).toMutableList()
        return stops
    }

    override suspend fun savePathStops(key: String, stops: List<PathStop>) {
        val json = gson.toJson(stops)

        prefs.edit {
            putString(key, json)
        }
    }
}
