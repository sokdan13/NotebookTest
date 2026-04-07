package com.example.notebooktest.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.usecase.AddTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateTaskViewModel(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _isTaskCreated = MutableStateFlow(false)
    val isTaskCreated: StateFlow<Boolean> = _isTaskCreated

    fun createTask(name: String, description: String, dateStart: Long, dateFinish: Long) {
        viewModelScope.launch {
            val task = Task(
                id = 0,
                name = name,
                description = description,
                dateStart = dateStart,
                dateFinish = dateFinish
            )
            addTaskUseCase(task)
            _isTaskCreated.value = true
        }
    }

    fun resetCreationFlag() {
        _isTaskCreated.value = false
    }
}