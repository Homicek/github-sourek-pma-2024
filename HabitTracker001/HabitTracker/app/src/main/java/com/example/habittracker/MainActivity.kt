package com.example.habittracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.databinding.ActivityMainBinding
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: HabitTrackerDatabase
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var habitttAdapter: HabitTimetableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializace databáze
        database = HabitTrackerDatabaseInstance.getDatabase(this)

        // Vložení testovacích dat
        //insertSampleCategory()

        // Inicializace RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        habitAdapter = HabitAdapter(getSampleNotes())
        binding.recyclerView.adapter = habitAdapter

        binding.fabAddNote.setOnClickListener {
            showAddHabitDialog()
        }

        // Inicializace RecyclerViewTT
        binding.recyclerViewTT.layoutManager = LinearLayoutManager(this)
        habitttAdapter = HabitTimetableAdapter(getSampleNotesTT())
        binding.recyclerViewTT.adapter = habitttAdapter

    }

//    private fun insertSampleCategory() {
//        lifecycleScope.launch {
//            val sampleCategories = listOf(
//                Habit(habitName = "Habit 1", categoryId = 1, habitFrekvency = 2),
//                Habit(habitName = "Habit 2", categoryId = 1, habitFrekvency = 1),
//                Habit(habitName = "Habit 3", categoryId = 2, habitFrekvency = 3),
//            )
//            sampleCategories.forEach { database.habitDao().insert(it) }
//        }
//    }

//získej seznam poznámek
    private fun getSampleNotes(): List<Habit> {
        // Testovací seznam poznámek
        return listOf(
            Habit(habitName = "Poznámka 1", categoryId = 1, habitFrekvency = 2),
            Habit(habitName = "Poznámka 2", categoryId = 4, habitFrekvency = 3),

        )
    }

    //získej seznam poznámek TT
    private fun getSampleNotesTT(): List<HabitTimetable> {
        // Testovací seznam poznámek
        return listOf(
            HabitTimetable(habitId = 1, habitTimetableStatus = "active", habitTimetableDay = 1, habitTimetableYear = 1, habitTimetableMonth = 1, HabitTimetableHour = 1),
            HabitTimetable(habitId = 2, habitTimetableStatus = "paused", habitTimetableDay = 1, habitTimetableYear = 2, habitTimetableMonth = 1, HabitTimetableHour = 1),
            HabitTimetable(habitId = 3, habitTimetableStatus = "finished", habitTimetableDay = 1, habitTimetableYear = 1, habitTimetableMonth = 3, HabitTimetableHour = 1),


            )
    }

//Zobrazení dialogového okna pro přidání Habitu
    private fun showAddHabitDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_habit, null)
        val editHabitName = dialogView.findViewById<EditText>(R.id.editHabitName)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)

    lifecycleScope.launch {
        val allHabits = database.habitDao().getAllHabits().first() // Načteme kategorie
        val categoryIds = allHabits.map { it.categoryId }  // Převedeme na seznam názvů kategorií
        val filteredCategoryIds = categoryIds.filterNotNull().distinct()  // Odfiltrujeme duplicitní názvy kategorií
        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, filteredCategoryIds)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

        val dialog = AlertDialog.Builder(this)
            .setTitle("New Habit")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = editHabitName.text.toString()
                val categoryId = spinnerCategory.selectedItemPosition
                addHabitToDatabase(name, categoryId)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }


    private fun addHabitToDatabase(habitName: String, categoryId: Int?) {
        lifecycleScope.launch {
            val newHabit = Habit(habitName = habitName, categoryId = categoryId)
            database.habitDao().insert(newHabit)  // Vloží poznámku do databáze
            loadHabits()  // Aktualizuje seznam poznámek
        }
    }

    private fun loadHabits() {
        lifecycleScope.launch {
            database.habitDao().getAllHabits().collect { habits ->
                habitAdapter = HabitAdapter(habits)
                binding.recyclerView.adapter = habitAdapter
            }
        }
    }
}