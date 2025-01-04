package com.aryandi.university.data.remote

import com.aryandi.university.data.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getUniversities(): Flow<ApiResult<List<NetworkUniversity>>>
}