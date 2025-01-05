package com.aryandi.university.data.repository

import com.aryandi.university.data.local.UniversitiesDao
import com.aryandi.university.data.remote.ApiService
import com.aryandi.university.domain.mapper.ApiToDBMapper
import com.aryandi.university.domain.mapper.ApiToModelMapper
import com.aryandi.university.domain.mapper.DBToModelMapper
import com.aryandi.university.domain.model.DataResult
import com.aryandi.university.domain.model.University
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val universitiesDao: UniversitiesDao,
    private val apiService: ApiService
) : UniversityRepository {
    override fun getUniversities(): Flow<DataResult<List<University>>> = flow {
        emit(DataResult.Loading())
        val dbUniversities = universitiesDao.getDBUniversities()
        if (dbUniversities.isEmpty()) {
            val apiUniversities = apiService.getUniversities().data
            apiUniversities?.apply {
                universitiesDao.insertDBUniversities(this.map {
                    ApiToDBMapper.map(it)
                })
                emit(DataResult.Success(this.map {
                    ApiToModelMapper.map(it)
                }))
            }
        } else {
            emit(DataResult.Success(dbUniversities.map {
                DBToModelMapper.map(it)
            }))
        }
    }

    override fun getUniversitiesByKeyword(keyword: String):
            Flow<DataResult<List<University>>> = flow {
        emit(DataResult.Loading())
        val dbUniversities = universitiesDao.getDBUniversitiesByKey(keyword)
        emit(DataResult.Success(dbUniversities.map {
            DBToModelMapper.map(it)
        }))
    }
}