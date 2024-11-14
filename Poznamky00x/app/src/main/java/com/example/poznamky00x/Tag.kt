package com.example.poznamky00x
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table")
data class Tag(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Automaticky generované ID štítku
    val name: String  // Název štítku
)