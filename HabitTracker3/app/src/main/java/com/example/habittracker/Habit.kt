package com.example.habittracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val priority: String,
    val dateTime: String? = null,
    val isCompleted: Boolean = false // Nový sloupec
)

