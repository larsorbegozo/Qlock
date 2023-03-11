package com.larsorbegozo.qlock.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo("name") val name: String
)
