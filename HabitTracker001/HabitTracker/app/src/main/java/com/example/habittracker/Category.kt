package com.example.habittracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table") //název tabulky

data class Category (
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //ID poznámky, automaticky generované
    val categoryName: String, //název poznámky
)