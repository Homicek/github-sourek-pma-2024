package com.example.habittracker

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LogsDao {

    // Vloží novou poznámku do databáze
    @Insert
    suspend fun insert(logs: Logs)

    // Aktualizuje existující poznámku
    @Update
    suspend fun update(logs: Logs)

    // Smaže zadanou poznámku
    @Delete
    suspend fun delete(logs: Logs)

    // Načte všechny poznámky a vrátí je jako Flow, které umožňuje pozorování změn
    @Query("SELECT * FROM logs_table ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Logs>>


}