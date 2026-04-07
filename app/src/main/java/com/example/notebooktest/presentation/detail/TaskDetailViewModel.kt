package com.example.notebooktest.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.usecase.GetTaskByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task

    fun loadTask(id: Int) {

        viewModelScope.launch {

            _task.value = getTaskByIdUseCase(id)

        }
    }
}