package com.example.notebooktest.data.mapper

import com.example.notebooktest.data.database.TaskEntity
import com.example.notebooktest.domain.model.Task

fun TaskEntity.toDomain() = Task(
    id,
    dateStart,
    dateFinish,
    name,
    description
)

fun Task.toEntity() = TaskEntity(
    id,
    dateStart,
    dateFinish,
    name,
    description
)