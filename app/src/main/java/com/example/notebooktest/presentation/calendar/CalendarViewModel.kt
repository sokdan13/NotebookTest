package com.example.notebooktest.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.usecase.GetTasksByDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class CalendarViewModel(
    private val getTasksByDateUseCase: GetTasksByDateUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _selectedDate = MutableStateFlow(getStartOfDay(System.currentTimeMillis()))
    val selectedDate: StateFlow<Long> = _selectedDate

    init {
        loadTasks()
    }

    fun setDate(date: Long) {
        _selectedDate.value = getStartOfDay(date)
        loadTasks()
    }

    fun refreshTasks() {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = getTasksByDateUseCase(_selectedDate.value)
        }
    }

    fun isTaskInHour(task: Task, hour: Int): Boolean {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = task.dateStart
        }
        val taskHour = calendar.get(Calendar.HOUR_OF_DAY)
        return taskHour == hour
    }

    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }
}