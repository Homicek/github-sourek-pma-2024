package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var database: HabitDatabase
    private lateinit var habitDao: HabitDao
    private lateinit var habitAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deleteDatabase("habit_database")

        // Inicializace databáze
        database = HabitDatabase.getDatabase(this)
        habitDao = database.habitDao()

        // Inicializace RecyclerView a adapteru
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_habits)
        habitAdapter = HabitAdapter(
            onCompletedChange = { habit, isCompleted ->
                // Aktualizace stavu dokončení v databázi
                CoroutineScope(Dispatchers.IO).launch {
                    database.habitDao().updateHabit(habit.copy(isCompleted = isCompleted))
                }
            },
            onEditClick = { habit ->
                // Spuštění aktivity pro úpravu návyku
                val intent = Intent(this, AddHabitActivity::class.java).apply {
                    putExtra("habit_id", habit.id)
                    putExtra("habit_name", habit.name)
                    putExtra("habit_category", habit.category)
                    putExtra("habit_priority", habit.priority)
                    putExtra("habit_dateTime", habit.dateTime)
                }
                startActivity(intent)
            },
            onDeleteClick = { habit ->
                // Mazání návyku
                AlertDialog.Builder(this)
                    .setTitle("Delete Habit")
                    .setMessage("Are you sure you want to delete this habit?")
                    .setPositiveButton("Yes") { _, _ ->
                        CoroutineScope(Dispatchers.IO).launch {
                            database.habitDao().deleteHabit(habit)
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        )


        recyclerView.adapter = habitAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Načtení návyků z databáze
        loadHabits()

        // Navigace do AddHabitActivity
        findViewById<Button>(R.id.btn_open_add_habit).setOnClickListener {
            val intent = Intent(this, AddHabitActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Načíst návyky znovu, když se aktivita vrátí na popředí
        loadHabits()
    }

    private fun loadHabits() {
        habitDao.getAllHabits().observe(this, { habits ->
            habitAdapter.setHabits(habits)
        })
    }



    private fun exportToICS(context: Context, plannedHabits: List<PlannedHabit>, habits: List<Habit>) {
        val fileName = "planned_habits.ics"
        val file = File(context.getExternalFilesDir(null), fileName)

        file.bufferedWriter().use { writer ->
            writer.write("BEGIN:VCALENDAR\n")
            writer.write("VERSION:2.0\n")
            writer.write("PRODID:-//Habit Tracker//EN\n")

            for (plannedHabit in plannedHabits) {
                val habit = habits.find { it.id == plannedHabit.habitId } ?: continue
                writer.write("BEGIN:VEVENT\n")
                writer.write("SUMMARY:${habit.name}\n")
                writer.write("DTSTART:${plannedHabit.date.replace("-", "")}T${plannedHabit.time?.replace(":", "") ?: "000000"}Z\n")
                writer.write("END:VEVENT\n")
            }

            writer.write("END:VCALENDAR\n")
        }

        // Informace o úspěšném uložení
        //Toast.makeText(context, "File exported to: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }


}
