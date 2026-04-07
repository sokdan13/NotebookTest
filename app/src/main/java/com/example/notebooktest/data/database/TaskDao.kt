package com.example.notebooktest.data.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getById(id: Int): TaskEntity?

    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity WHERE dateStart >= :start AND dateStart <= :end ORDER BY dateStart ASC")
    suspend fun getTasksByDateRange(start: Long, end: Long): List<TaskEntity>
}