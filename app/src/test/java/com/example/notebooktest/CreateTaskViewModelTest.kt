package com.example.notebooktest

import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.domain.repository.TaskRepository
import com.example.notebooktest.domain.usecase.AddTaskUseCase
import com.example.notebooktest.presentation.create.CreateTaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateTaskViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `create task should add task to repository`() = runTest {
        // Given
        var taskAdded = false
        var addedTask: Task? = null

        val fakeRepo = object : TaskRepository {
            override suspend fun addTask(task: Task) {
                taskAdded = true
                addedTask = task
            }

            override suspend fun getAllTasks(): List<Task> = emptyList()
            override suspend fun getTaskById(id: Int): Task? = null
            override suspend fun getTasksByDate(date: Long): List<Task> = emptyList()
            override suspend fun getTasksByDateRange(start: Long, end: Long): List<Task> = emptyList()
        }

        val viewModel = CreateTaskViewModel(AddTaskUseCase(fakeRepo))

        viewModel.createTask(
            name = "Test Task",
            description = "Test Description",
            dateStart = 1680806400000,
            dateFinish = 1680810000000
        )

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue("Task should be added to repository", taskAdded)
        assertNotNull("Added task should not be null", addedTask)
        assertEquals("Task name should match", "Test Task", addedTask?.name)
        assertEquals("Task description should match", "Test Description", addedTask?.description)
        assertEquals("Task dateStart should match", 1680806400000, addedTask?.dateStart)
        assertEquals("Task dateFinish should match", 1680810000000, addedTask?.dateFinish)
    }
}