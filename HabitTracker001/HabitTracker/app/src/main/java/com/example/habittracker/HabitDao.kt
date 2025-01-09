package com.example.habittracker

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    // Vloží novou poznámku do databáze
    @Insert
    suspend fun insert(habit: Habit)

    // Aktualizuje existující poznámku
    @Update
    suspend fun update(habit: Habit)

    // Smaže zadanou poznámku
    @Delete
    suspend fun delete(habit: Habit)

    // Načte všechny poznámky a vrátí je jako Flow, které umožňuje pozorování změn
    @Query("SELECT * FROM habit_table ORDER BY id DESC")
    fun getAllHabits(): Flow<List<Habit>>


}