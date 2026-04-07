package com.example.notebooktest.presentation.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notebooktest.presentation.calendar.formatDate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    viewModel: CreateTaskViewModel,
    onBack: (Boolean) -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }
    var hour by remember { mutableStateOf(12) }
    var minute by remember { mutableStateOf(0) }

    val isTaskCreated by viewModel.isTaskCreated.collectAsStateWithLifecycle()

    LaunchedEffect(isTaskCreated) {
        if (isTaskCreated) {
            viewModel.resetCreationFlag()
            onBack(true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Создать задачу") },
                navigationIcon = {
                    IconButton(onClick = { onBack(false) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                showDatePicker(context, date) { selected ->
                    date = selected
                }
            }) {
                Text("Выбрать дату: ${formatDate(date)}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                showTimePicker(context, hour, minute) { h, m ->
                    hour = h
                    minute = m
                }
            }) {
                Text("Выбрать время: %02d:%02d".format(hour, minute))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = date
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val start = calendar.timeInMillis
                    val finish = start + 3600000L // 1 час
                    viewModel.createTask(name, description, start, finish)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && description.isNotBlank()
            ) {
                Text("Создать задачу")
            }
        }
    }
}

fun showDatePicker(context: android.content.Context, currentDate: Long, onSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance().apply { timeInMillis = currentDate }
    DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            onSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun showTimePicker(context: android.content.Context, currentHour: Int, currentMinute: Int, onSelected: (Int, Int) -> Unit) {
    TimePickerDialog(
        context,
        { _, hour, minute -> onSelected(hour, minute) },
        currentHour,
        currentMinute,
        true
    ).show()
}