package com.example.notebooktest.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notebooktest.domain.model.Task
import com.example.notebooktest.presentation.calendar.formatTime

@Composable
fun HourRow(
    hour: Int,
    task: Task?,
    onClick: (Task?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = String.format("%02d:00-%02d:00", hour, hour + 1),
            modifier = Modifier.width(100.dp)
        )

        if (task != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(task) }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = task.name, style = MaterialTheme.typography.titleSmall)
                    Text(text = "${formatTime(task.dateStart)} - ${formatTime(task.dateFinish)}")
                }
            }
        } else {
            Divider(modifier = Modifier.fillMaxWidth())
        }
    }
}