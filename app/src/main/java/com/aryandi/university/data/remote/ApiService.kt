package com.aryandi.university.data.remote

interface ApiService {
    suspend fun getUniversities(): ApiResult<List<ApiUniversity>>
}