package com.aryandi.university.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UniversitiesDao {
    @Query("SELECT * FROM university")
    suspend fun getDBUniversities(): List<DBUniversity>

    @Query("SELECT * FROM university WHERE name LIKE '%' || :key || '%'")
    suspend fun getDBUniversitiesByKey(key:String): List<DBUniversity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDBUniversities(universities: List<DBUniversity>)
}
