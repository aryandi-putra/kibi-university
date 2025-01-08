package com.aryandi.university.viewmodel

import app.cash.turbine.test
import com.aryandi.university.data.repository.UniversityRepository
import com.aryandi.university.domain.model.DataResult
import com.aryandi.university.domain.model.University
import com.aryandi.university.feature.home.MainViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val repository: UniversityRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `Get universities should show correct list`(): Unit = runTest {
        val dummyUniversity = listOf(
            University("Universitas Lampung", "Indonesia", "www.unila.ac.id"),
            University("Universitas Indonesia", "Indonesia", "www.ui.ac.id")
        )

        val successResult = DataResult.Success(dummyUniversity)

        coEvery { repository.getUniversities() }.coAnswers {
            flow {
                emit(DataResult.Loading())
                emit(successResult)
            }
        }

        val dispatchers = StandardTestDispatcher(testScheduler)

        viewModel = MainViewModel(repository, dispatchers)

        viewModel.universities.test {
            // first Data Result Loading is init
            assertTrue(awaitItem() is DataResult.Loading)
            assertTrue(awaitItem() is DataResult.Loading)
            assertEquals(successResult, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify { repository.getUniversities() }
    }

    @Test
    fun `Get universities should show error`(): Unit = runTest {
        coEvery { repository.getUniversities() }.coAnswers {
            flow {
                emit(DataResult.Loading())
                emit(DataResult.Error("Something went wrong"))
            }
        }

        val dispatchers = StandardTestDispatcher(testScheduler)

        viewModel = MainViewModel(repository, dispatchers)

        viewModel.universities.test {
            // first Data Result Loading is init
            assertTrue(awaitItem() is DataResult.Loading)
            assertTrue(awaitItem() is DataResult.Loading)
            assertTrue(awaitItem() is DataResult.Error)
            cancelAndIgnoreRemainingEvents()
        }
        verify { repository.getUniversities() }
    }
}