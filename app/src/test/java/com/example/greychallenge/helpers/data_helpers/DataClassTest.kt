package com.example.greychallenge.helpers.data_helpers

import app.cash.turbine.test
import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mocks.MockPathLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataClassTest {

    private lateinit var fakeStorage: MockPathLocalDataSource
    private lateinit var dataClass: DataClass

    @Before
    fun setup() {
        fakeStorage = MockPathLocalDataSource()
        dataClass = DataClass(fakeStorage)
    }

    @Test
    fun `loadFromStorage updates pathStops flow`() = runTest {
        val stops = listOf(
            PathStop(
                id = 1,
                title = "A",
                subtitle = "a",
                progressText = "A",
                progress = 0f
            ),
            PathStop(
                id = 2,
                title = "B",
                subtitle = "b",
                progressText = "B",
                progress = 0f
            )
        )

        fakeStorage.savePathStops("path_stops", stops)

        dataClass.pathStops.test {
            awaitItem()
            dataClass.loadFromStorage()

            val result = awaitItem()

            assertEquals(stops, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `saveToStorage persists and updates flow`() = runTest {
        val stops = listOf(
            PathStop(
                id = 10,
                title = "Home",
                subtitle = "home",
                progressText = "home",
                progress = 0f
            ),
            PathStop(
                id = 20,
                title = "Office",
                subtitle = "office",
                progressText = "office",
                progress = 0f
            )
        )

        dataClass.pathStops.test {
            awaitItem()
            dataClass.saveToStorage(stops)

            val result = awaitItem()

            assertEquals(stops, result)
            cancelAndIgnoreRemainingEvents()
        }

        val stored = fakeStorage.loadPathStops("path_stops")
        assertEquals(stops, stored)
    }
}
