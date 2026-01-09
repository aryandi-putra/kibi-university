package com.aryandi.university.di

import android.content.Context
import androidx.room.Room
import com.aryandi.university.data.local.UniversitiesDao
import com.aryandi.university.data.local.UniversitiesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun providesUniversitiesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            UniversitiesDatabase::class.java, "universities_db"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providesUniversitiesDao(database: UniversitiesDatabase): UniversitiesDao =
        database.universitiesDao()
}