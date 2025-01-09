package com.example.habittracker

import android.content.Context
import androidx.room.Room

object HabitTrackerDatabaseInstance {
    // Lazy inicializace instance databáze
    @Volatile
    private var INSTANCE: HabitTrackerDatabase? = null

    // Funkce pro získání instance databáze
    fun getDatabase(context: Context): HabitTrackerDatabase {
        // Pokud je instance null, inicializuje ji
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                HabitTrackerDatabase::class.java,
                "habit_tracker_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}