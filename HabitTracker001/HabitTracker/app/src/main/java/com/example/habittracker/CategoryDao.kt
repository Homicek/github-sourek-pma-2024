package com.example.habittracker

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // Vloží novou poznámku do databáze
    @Insert
    suspend fun insert(category: Category)

    // Aktualizuje existující poznámku
    @Update
    suspend fun update(category: Category)

    // Smaže zadanou poznámku
    @Delete
    suspend fun delete(category: Category)

    // Načte všechny poznámky a vrátí je jako Flow, které umožňuje pozorování změn
    @Query("SELECT * FROM category_table ORDER BY id DESC")
    fun getAllCategories(): Flow<List<Category>>


}