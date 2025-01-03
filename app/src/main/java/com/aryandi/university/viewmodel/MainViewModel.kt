package com.aryandi.university.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryandi.university.data.model.ApiResult
import com.aryandi.university.data.model.University
import com.aryandi.university.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _universities = MutableStateFlow<ApiResult<List<University>>>(ApiResult.Loading())
    val universities = _universities.asStateFlow()

    init {
        fetchUniversities()
    }

    private fun fetchUniversities() {
        viewModelScope.launch {
            apiService.getUniversities()
                .flowOn(defaultDispatcher)
                .catch {
                    _universities.value = ApiResult.Error(it.message ?: "Something went wrong")
                }
                .collect {
                    _universities.value = it
                }
        }
    }
}