package com.example.habittracker

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitTimetableDao {

    // Vloží novou poznámku do databáze
    @Insert
    suspend fun insert(habitTimetable: HabitTimetable)

    // Aktualizuje existující poznámku
    @Update
    suspend fun update(habitTimetable: HabitTimetable)

    // Smaže zadanou poznámku
    @Delete
    suspend fun delete(HabitTimetable: HabitTimetable)

    // Načte všechny poznámky a vrátí je jako Flow, které umožňuje pozorování změn
    @Query("SELECT * FROM habit_timetable ORDER BY id DESC")
    fun getAllNotes(): Flow<List<HabitTimetable>>


}