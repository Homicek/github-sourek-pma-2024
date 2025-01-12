package com.example.habittracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "habit_table")

data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val habitName: String,
    val categoryName: String, //volitelný odkaz na ktegorii
    val habitFrekvency: Int? = null, //volitelná frekvence,
)
