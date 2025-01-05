package com.aryandi.university.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryandi.university.data.remote.ApiResult
import com.aryandi.university.data.remote.ApiUniversity
import com.aryandi.university.data.remote.ApiService
import com.aryandi.university.data.repository.UniversityRepository
import com.aryandi.university.domain.model.DataResult
import com.aryandi.university.domain.model.University
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
    private val repository: UniversityRepository,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _universities = MutableStateFlow<DataResult<List<University>>>(DataResult.Loading())
    val universities = _universities.asStateFlow()

    init {
        fetchUniversities()
    }

    private fun fetchUniversities() {
        viewModelScope.launch {
            repository.getUniversities()
                .flowOn(defaultDispatcher)
                .catch {
                    _universities.value = DataResult.Error(it.message ?: "Something went wrong")
                }
                .collect {
                    _universities.value = it
                }
        }
    }
}