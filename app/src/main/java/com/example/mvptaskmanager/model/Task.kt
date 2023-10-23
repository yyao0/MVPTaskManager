package com.example.mvptaskmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val time: String,
    val status: String,
    val content: String
)
