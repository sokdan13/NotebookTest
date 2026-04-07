package com.example.notebooktest.data.database

import androidx.room3.Entity

@Entity
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val dateStart: Long,
    val dateFinish: Long,

    val name: String,
    val description: String
)