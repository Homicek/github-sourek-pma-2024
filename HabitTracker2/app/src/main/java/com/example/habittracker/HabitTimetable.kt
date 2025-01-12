package com.example.habittracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "habit_timetable")

data class HabitTimetable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int,
    val habitTimetableDay: Int,
    val habitTimetableMonth: Int,
    val habitTimetableYear: Int,
    val HabitTimetableHour: Int,
    val habitTimetableStatus: String

    )
