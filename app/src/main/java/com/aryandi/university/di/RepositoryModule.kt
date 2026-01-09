package com.aryandi.university.di

import com.aryandi.university.data.local.UniversitiesDao
import com.aryandi.university.data.remote.ApiService
import com.aryandi.university.data.repository.UniversityRepository
import com.aryandi.university.data.repository.UniversityRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        universitiesDao: UniversitiesDao,
        apiService: ApiService
    ): UniversityRepository = UniversityRepositoryImpl(
        universitiesDao, apiService
    )
}