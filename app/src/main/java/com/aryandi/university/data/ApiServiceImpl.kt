package com.aryandi.university.data

import com.aryandi.university.data.model.University
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiServiceImpl constructor(private val httpClient: HttpClient) : ApiService {
    override fun getUniversities(): Flow<ApiResult<List<University>>> = flow {
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get("/search") {
                parameter("country", "indonesia")
            }.body()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }
}