package com.example.notebooktest.presentation.calendar

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.notebooktest.presentation.components.DateSelector
import com.example.notebooktest.presentation.components.HourRow
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onTaskClick: (Int) -> Unit,
    onCreateClick: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ежедневник") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateClick) { Text("+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DateSelector(viewModel, context)
            Divider()

            LazyColumn {
                items(24) { hour ->
                    val task = tasks.find { viewModel.isTaskInHour(it, hour) }
                    HourRow(
                        hour = hour,
                        task = task,
                        onClick = { it?.let { task -> onTaskClick(task.id) } }
                    )
                }
            }
        }
    }
}



fun showDatePicker(context: Context, currentDate: Long, onDateSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance().apply { timeInMillis = currentDate }
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            onDateSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}


fun formatDate(time: Long): String =
    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(time))

fun formatTime(time: Long): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(time))