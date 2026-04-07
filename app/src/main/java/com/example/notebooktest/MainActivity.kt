package com.example.notebooktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import androidx.room3.Room
import com.example.notebooktest.data.database.AppDatabase
import com.example.notebooktest.data.repository.TaskRepositoryImpl
import com.example.notebooktest.domain.usecase.AddTaskUseCase
import com.example.notebooktest.domain.usecase.GetTaskByIdUseCase
import com.example.notebooktest.domain.usecase.GetTasksByDateUseCase
import com.example.notebooktest.presentation.calendar.CalendarScreen
import com.example.notebooktest.presentation.calendar.CalendarViewModel
import com.example.notebooktest.presentation.create.CreateTaskScreen
import com.example.notebooktest.presentation.create.CreateTaskViewModel
import com.example.notebooktest.presentation.detail.TaskDetailScreen
import com.example.notebooktest.presentation.detail.TaskDetailViewModel
import com.example.notebooktest.ui.theme.NotebookTestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tasks_db"
        ).build()

        val repository = TaskRepositoryImpl(db.taskDao())

        val getTasksByDateUseCase = GetTasksByDateUseCase(repository)
        val getTaskByIdUseCase = GetTaskByIdUseCase(repository)
        val addTaskUseCase = AddTaskUseCase(repository)

        val calendarViewModel = CalendarViewModel(getTasksByDateUseCase)
        val createViewModel = CreateTaskViewModel(addTaskUseCase)
        val detailViewModel = TaskDetailViewModel(getTaskByIdUseCase)

        setContent {
            NotebookTestTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "calendar"
                ) {
                    composable("calendar") {
                        CalendarScreen(
                            viewModel = calendarViewModel,
                            onTaskClick = { id ->
                                navController.navigate("detail/$id")
                            },
                            onCreateClick = {
                                navController.navigate("create")
                            }
                        )
                    }

                    composable("create") {
                        CreateTaskScreen(
                            viewModel = createViewModel,
                            onBack = {
                                // Обновляем список при возврате
                                calendarViewModel.refreshTasks()
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("detail/{id}") { backStack ->
                        val id = backStack.arguments?.getString("id")?.toInt() ?: 0
                        detailViewModel.loadTask(id)
                        TaskDetailScreen(viewModel = detailViewModel)
                    }
                }
            }
        }
    }
}