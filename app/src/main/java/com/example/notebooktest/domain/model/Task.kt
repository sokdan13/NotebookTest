package com.example.notebooktest.domain.model

data class Task(
    val id: Int,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
)