package com.example.notebooktest.data.database

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getById(id: Int): TaskEntity?

    @Insert
    suspend fun insert(task: TaskEntity)
}