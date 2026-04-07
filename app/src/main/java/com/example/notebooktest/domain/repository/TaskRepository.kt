package com.example.notebooktest.domain.repository

import com.example.notebooktest.domain.model.Task

interface TaskRepository {

    suspend fun getAllTasks(): List<Task>

    suspend fun getTasksByDate(date: Long): List<Task>

    suspend fun getTaskById(id: Int): Task?

    suspend fun addTask(task: Task)
}