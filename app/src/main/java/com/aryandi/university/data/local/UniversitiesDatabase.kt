package com.aryandi.university.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBUniversity::class], version = 1, exportSchema = false)
abstract class UniversitiesDatabase : RoomDatabase() {
    abstract fun universitiesDao(): UniversitiesDao
}