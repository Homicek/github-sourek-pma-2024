package com.example.habittracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Category::class, Habit::class, HabitTimetable::class, Logs::class],
    version = 1,
    exportSchema = false

)

abstract class HabitTrackerDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun habitDao(): HabitDao
    abstract fun habitTimetableDao(): HabitTimetableDao
    abstract fun logsDao(): LogsDao


}