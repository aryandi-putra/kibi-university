package com.aryandi.university.data.remote

import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getUniversities(): ApiResult<List<ApiUniversity>>
}