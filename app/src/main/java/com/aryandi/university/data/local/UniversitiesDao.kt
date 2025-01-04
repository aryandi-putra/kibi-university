package com.aryandi.university.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UniversitiesDao {
    @Query("SELECT * FROM university")
    fun getAll(): List<DBUniversity>

    @Query("SELECT * FROM university WHERE name = :key")
    fun getAllByKey(key:String): List<DBUniversity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(universities: List<DBUniversity>)
}
