package com.example.notebooktest.data.database

import android.annotation.SuppressLint
import androidx.room3.Database
import androidx.room3.RoomDatabase

@SuppressLint("RestrictedApi")
@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}