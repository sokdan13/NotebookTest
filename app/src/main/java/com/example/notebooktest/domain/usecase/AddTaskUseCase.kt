package com.example.notebooktest.domain.usecase

import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) =
        repository.addTask(task)
}