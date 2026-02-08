package com.example.greychallenge.mvvm.viewModel

import app.cash.turbine.test
import com.example.greychallenge.helpers.data_helpers.DataClass
import com.example.greychallenge.mocks.MockPathLocalDataSource
import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mvvm.view_models.PathViewModel
import com.example.greychallenge.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PathViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var fakeStorage: MockPathLocalDataSource
    private lateinit var container: DataClass
    private lateinit var viewModel: PathViewModel

    @Before
    fun setup() {
        fakeStorage = MockPathLocalDataSource()
        container = DataClass(fakeStorage)
    }

    @Test
    fun `init loads data from storage`() = runTest {
        val stops = listOf(
            PathStop(1, "A", "a", "A", 0f),
            PathStop(2, "B", "b", "B", 0f)
        )

        fakeStorage.savePathStops("path_stops", stops)

        viewModel = PathViewModel(container)
        advanceUntilIdle()

        viewModel.stops.test {
            val result = awaitItem()

            assertEquals(stops, result)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `saveStops updates storage and flow`() = runTest {
        viewModel = PathViewModel(container)
        advanceUntilIdle()

        val stops = listOf(
            PathStop(1, "A", "a", "A", 0f),
            PathStop(2, "B", "b", "B", 0f)
        )

        viewModel.stops.test {
            awaitItem()

            viewModel.saveStops(stops)
            advanceUntilIdle()

            val result = awaitItem()
            assertEquals(stops, result)
            cancelAndIgnoreRemainingEvents()
        }

        val stored = fakeStorage.loadPathStops("path_stops")
        assertEquals(stops, stored)
    }

}
