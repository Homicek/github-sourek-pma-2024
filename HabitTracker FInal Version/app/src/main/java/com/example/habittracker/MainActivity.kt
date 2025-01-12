package com.example.habittracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.Habit
import com.example.habittracker.HabitAdapter
import com.example.habittracker.HabitDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var database: HabitDatabase
    private lateinit var habitAdapter: HabitAdapter

    private fun deleteDatabase() {
        this.deleteDatabase("habit_database")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // deleteDatabase()

        // Inicializace databáze
        database = HabitDatabase.getDatabase(this)

        // Inicializace RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        habitAdapter = HabitAdapter(
            onCompletedChange = { habit, isCompleted ->
                CoroutineScope(Dispatchers.IO).launch {
                    database.habitDao().updateHabit(habit.copy(isCompleted = isCompleted))
                }
            },

            onDeleteClick = { habit ->
                CoroutineScope(Dispatchers.IO).launch {
                    database.habitDao().deleteHabit(habit)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Habit deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        recyclerView.adapter = habitAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializace Spinneru pro filtrování
        val spFilter = findViewById<Spinner>(R.id.sp_filter)
        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> loadAllHabits() // "All Habits"
                    1 -> loadIncompleteHabits() // "Incomplete Habits"
                    2 -> loadHabitsByCategory("Home") // "Home"
                    3 -> loadHabitsByCategory("Work") // "Work"
                    4 -> loadHabitsByCategory("Other") // "Other"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Pokud není nic vybráno, nemusíte dělat nic
            }
        }

        val btnAddHabit = findViewById<Button>(R.id.btn_open_add_habit)
        btnAddHabit.setOnClickListener {
            val intent = Intent(this, AddHabitActivity::class.java)
            startActivity(intent)
        }

        // Tlačítko pro export do .ics
        val btnExport = findViewById<Button>(R.id.btn_export)
        btnExport.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val habits = database.habitDao().getAllHabitsList()
                withContext(Dispatchers.Main) {
                    if (habits.isEmpty()) {
                        Toast.makeText(this@MainActivity, "No habits to export", Toast.LENGTH_SHORT).show()
                    } else {
                        exportToICS(habits)
                    }
                }
            }
        }

        // Načtení všech habitů při spuštění
        loadAllHabits()
    }

    // Načtení všech habitů
    private fun loadAllHabits() {
        database.habitDao().getAllHabits().observe(this, Observer { habits ->
            habitAdapter.setHabits(habits)
        })
    }

    // Načtení neukončených habitů
    private fun loadIncompleteHabits() {
        database.habitDao().getIncompleteHabits().observe(this, Observer { habits ->
            habitAdapter.setHabits(habits)
        })
    }

    // Načtení habitů podle kategorie
    private fun loadHabitsByCategory(category: String) {
        database.habitDao().getHabitsByCategory(category).observe(this, Observer { habits ->
            habitAdapter.setHabits(habits)
        })
    }

    // Export do .ics
    private fun exportToICS(habits: List<Habit>) {
        val habitsWithDateTime = habits.filter { it.dateTime != null && it.dateTime.isNotEmpty() }
        if (habitsWithDateTime.isEmpty()) {
            Toast.makeText(this, "No valid habits to export", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val fileName = "habits_export.ics"
            val file = File(getExternalFilesDir(null), fileName)
            val writer = FileWriter(file)

            // Hlavička .ics souboru
            writer.write("BEGIN:VCALENDAR\n")
            writer.write("VERSION:2.0\n")
            writer.write("CALSCALE:GREGORIAN\n")

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val icsFormatter = SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.getDefault())

            // Zápis jednotlivých událostí
            habitsWithDateTime.forEach { habit ->
                try {
                    val startDate = formatter.parse(habit.dateTime!!)
                    val startDateFormatted = icsFormatter.format(startDate!!)
                    val endDateFormatted = icsFormatter.format(Date(startDate.time + 3600000)) // Přidá 1 hodinu

                    writer.write("BEGIN:VEVENT\n")
                    writer.write("SUMMARY:${habit.name}\n")
                    writer.write("DTSTART:$startDateFormatted\n")
                    writer.write("DTEND:$endDateFormatted\n")
                    writer.write("DESCRIPTION:Category: ${habit.category}, Priority: ${habit.priority}\n")
                    writer.write("END:VEVENT\n")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("ExportDebug", "Failed to process habit: ${habit.name}, Error: ${e.message}")
                }
            }

            // Ukončení souboru
            writer.write("END:VCALENDAR\n")
            writer.close()

            // Sdílení souboru
            shareICSFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Sdílení .ics souboru
    private fun shareICSFile(file: File) {
        val uri = androidx.core.content.FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/calendar"
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        startActivity(Intent.createChooser(intent, "Share Calendar File"))
    }
}












