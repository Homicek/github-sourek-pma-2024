package com.example.habittracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs_table")

data class Logs(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int,
    val habitTimetableId: Int,
    val logNote: String,

    )
