package com.example.notebooktest.domain.usecase

import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.repository.TaskRepository

class GetTasksByDateUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(date: Long): List<Task> {
        return repository.getTasksByDate(date)
    }
}