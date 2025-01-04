package com.aryandi.university.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "university")
data class DBUniversity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "country")
    var country: String,
    @ColumnInfo(name = "web_page")
    var webPage: String
)