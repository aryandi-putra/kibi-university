package com.aryandi.university.domain.model

sealed class DataResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : DataResult<T>(data = data)
    class Error<T>(error: String) : DataResult<T>(error = error)
    class Loading<T> : DataResult<T>()
}