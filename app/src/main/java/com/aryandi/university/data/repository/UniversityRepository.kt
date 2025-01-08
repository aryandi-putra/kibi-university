package com.aryandi.university.data.repository

import com.aryandi.university.domain.model.DataResult
import com.aryandi.university.domain.model.University
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {
    fun getUniversities() : Flow<DataResult<List<University>>>
    fun getUniversitiesByKeyword(keyword: String): Flow<DataResult<List<University>>>
}