package com.example.notebooktest.data.repository

import com.example.notebooktest.data.database.TaskDao
import com.example.notebooktest.data.mapper.toDomain
import com.example.notebooktest.data.mapper.toEntity
import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {

    override suspend fun getAllTasks(): List<Task> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun getTasksByDateRange(
        start: Long,
        end: Long
    ): List<Task> {
        return dao.getTasksByDateRange(start, end).map { it.toDomain() }
    }

    override suspend fun getTasksByDate(date: Long): List<Task> {
        val startOfDay = getStartOfDay(date)
        val endOfDay = getEndOfDay(date)
        return dao.getTasksByDateRange(startOfDay, endOfDay).map { it.toDomain() }
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun addTask(task: Task) {
        dao.insert(task.toEntity())
    }

    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(java.util.Calendar.HOUR_OF_DAY, 23)
            set(java.util.Calendar.MINUTE, 59)
            set(java.util.Calendar.SECOND, 59)
            set(java.util.Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }
}