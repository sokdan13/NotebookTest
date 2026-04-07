package com.example.notebooktest.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notebooktest.presentation.calendar.CalendarViewModel
import com.example.notebooktest.presentation.calendar.formatDate
import com.example.notebooktest.presentation.calendar.showDatePicker

@Composable
fun DateSelector(viewModel: CalendarViewModel, context: Context) {
    val date by viewModel.selectedDate.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formatDate(date),
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = {
            showDatePicker(context, date) { newDate ->
                viewModel.setDate(newDate)
            }
        }) {
            Text("Выбрать дату")
        }
    }
}