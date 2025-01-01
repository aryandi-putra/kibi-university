package com.aryandi.university.data

import com.aryandi.university.data.model.University
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getUniversities(): Flow<ApiResult<List<University>>>
}