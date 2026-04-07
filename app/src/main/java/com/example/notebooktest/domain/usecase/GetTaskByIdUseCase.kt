package com.example.notebooktest.domain.usecase

import com.example.notebooktest.domain.repository.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int) =
        repository.getTaskById(id)
}