package com.larsorbegozo.qlock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.larsorbegozo.qlock.data.model.Settings

@Database(entities = [Settings::class], version = 1, exportSchema = false)
abstract class ClockDatabase : RoomDatabase() {
    abstract fun ClockDao(): ClockDao

    companion object {
        @Volatile
        private var INSTANCE: ClockDatabase? = null

        fun getDatabase(context: Context): ClockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClockDatabase::class.java,
                    "clock_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}