package com.aryandi.university.repository

import app.cash.turbine.test
import com.aryandi.university.data.local.DBUniversity
import com.aryandi.university.data.local.UniversitiesDao
import com.aryandi.university.data.remote.ApiResult
import com.aryandi.university.data.remote.ApiService
import com.aryandi.university.data.remote.ApiUniversity
import com.aryandi.university.data.repository.UniversityRepository
import com.aryandi.university.data.repository.UniversityRepositoryImpl
import com.aryandi.university.domain.model.DataResult
import com.aryandi.university.domain.model.University
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private lateinit var repository: UniversityRepository
    private val universitiesDao: UniversitiesDao = mockk(relaxed = true)
    private val apiService: ApiService = mockk(relaxed = true)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `Get universities When there are no data on room should fetch network data`(): Unit =
        runTest {
            coEvery { universitiesDao.getDBUniversities() }.coAnswers {
                listOf()
            }

            val dummyAPIdata = listOf(
                ApiUniversity("Universitas Lampung", "Indonesia", listOf("www.unila.ac.id")),
                ApiUniversity("Universitas Indonesia", "Indonesia", listOf("www.ui.ac.id"))
            )

            coEvery { apiService.getUniversities() }.coAnswers {
                ApiResult.Success(dummyAPIdata)
            }

            repository = UniversityRepositoryImpl(universitiesDao, apiService)
            val result = repository.getUniversities()

            val expectedUniversities =
                listOf(
                    University("Universitas Lampung", "Indonesia", "www.unila.ac.id"),
                    University("Universitas Indonesia", "Indonesia", "www.ui.ac.id")
                )
            result.test {
                assertTrue(awaitItem() is DataResult.Loading)
                assertEquals(expectedUniversities, awaitItem().data)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Get universities When there are data exist on room should return cached data and don't fetch network`(): Unit =
        runTest {
            val dummyDBdata = listOf(
                DBUniversity(1,"Universitas Lampung", "Indonesia", "www.unila.ac.id"),
                DBUniversity(2, "Universitas Indonesia", "Indonesia", "www.ui.ac.id")
            )

            coEvery { universitiesDao.getDBUniversities() }.coAnswers {
                dummyDBdata
            }

            repository = UniversityRepositoryImpl(universitiesDao, apiService)
            val result = repository.getUniversities()

            val expectedUniversities =
                listOf(
                    University("Universitas Lampung", "Indonesia", "www.unila.ac.id"),
                    University("Universitas Indonesia", "Indonesia", "www.ui.ac.id")
                )
            result.test {
                assertTrue(awaitItem() is DataResult.Loading)
                assertEquals(expectedUniversities, awaitItem().data)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Get universities with keyword should return filtered cached data`(): Unit =
        runTest {
            val dummyDBdata = listOf(
                DBUniversity(1,"Universitas Lampung", "Indonesia", "www.unila.ac.id"),
                DBUniversity(2, "Universitas Indonesia", "Indonesia", "www.ui.ac.id")
            )

            val query = "Universitas"

            coEvery { universitiesDao.getDBUniversitiesByKey(query) }.coAnswers {
                dummyDBdata
            }

            repository = UniversityRepositoryImpl(universitiesDao, apiService)
            val result = repository.getUniversitiesByKeyword(query)

            val expectedUniversities =
                listOf(
                    University("Universitas Lampung", "Indonesia", "www.unila.ac.id"),
                    University("Universitas Indonesia", "Indonesia", "www.ui.ac.id")
                )
            result.test {
                assertTrue(awaitItem() is DataResult.Loading)
                assertEquals(expectedUniversities, awaitItem().data)
                cancelAndIgnoreRemainingEvents()
            }
        }
}