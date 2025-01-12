package com.example.habittracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Import vašich entit a DAO
import com.example.habittracker.Habit
import com.example.habittracker.PlannedHabit
import com.example.habittracker.HabitDao

@Database(
    entities = [Habit::class, PlannedHabit::class], // Zde uvedete všechny entity
    version = 3, // Verze databáze
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        /**
         * Vrací instanci databáze (singleton pattern).
         */
        fun getDatabase(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                )
                    .addMigrations(MIGRATION_2_3) // Přidejte migrace zde
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Definice migrace pro přechod z verze 2 na verzi 3
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Vytvoření nové tabulky habits s upravenou strukturou
                database.execSQL(
                    """
                    CREATE TABLE habits_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        category TEXT NOT NULL,
                        priority TEXT NOT NULL,
                        dateTime TEXT
                    )
                    """.trimIndent()
                )

                // Zkopírování dat ze staré tabulky do nové tabulky
                database.execSQL(
                    """
                    INSERT INTO habits_new (id, name, category, priority, dateTime)
                    SELECT id, name, category, priority, time
                    FROM habits
                    """.trimIndent()
                )

                // Odstranění staré tabulky
                database.execSQL("DROP TABLE habits")

                // Přejmenování nové tabulky na původní název
                database.execSQL("ALTER TABLE habits_new RENAME TO habits")
            }
        }
    }
}
