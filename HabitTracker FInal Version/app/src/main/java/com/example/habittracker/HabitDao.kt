package com.example.habittracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    // Operace pro návyky
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): LiveData<List<Habit>>

    // Operace pro plánované návyky
   // @Insert(onConflict = OnConflictStrategy.REPLACE)
   // suspend fun insertPlannedHabit(plannedHabit: PlannedHabit)

  //  @Query("SELECT * FROM planned_habits WHERE date = :date")
  //  fun getPlannedHabitsForDate(date: String): LiveData<List<PlannedHabit>>
    @Delete
    suspend fun deleteHabit(habit: Habit)
    @Update
    suspend fun updateHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE isCompleted = 0")
    fun getIncompleteHabits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE category = :category")
    fun getHabitsByCategory(category: String): LiveData<List<Habit>>

        @Query("SELECT * FROM habits")
        fun getAllHabitsList(): List<Habit> // Přímý návrat seznamu habitů



}
