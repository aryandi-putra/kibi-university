package com.aryandi.university.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override suspend fun getUniversities(): ApiResult<List<ApiUniversity>> {
        try {
            return ApiResult.Success(httpClient.get("/search") {
                parameter("country", "indonesia")
            }.body())
        } catch (e: Exception) {
            e.printStackTrace()
            return ApiResult.Error(e.message ?: "Something went wrong")
        }
    }
}