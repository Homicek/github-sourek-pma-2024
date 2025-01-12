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
import kotlinx.coroutines.flow.toList
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

        database.habitDao().delete()

        // Vložení testovacích dat
        //insertSampleCategory()

        // Inicializace RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val habitList: List<Habit> = database.habitDao().getAllHabits().first()
            habitAdapter = HabitAdapter(habitList)
            binding.recyclerView.adapter = habitAdapter
        }

        binding.fabAddNote.setOnClickListener {
            showAddHabitDialog()
        }

        // Inicializace RecyclerViewTT
        binding.recyclerViewTT.layoutManager = LinearLayoutManager(this)
        habitttAdapter = HabitTimetableAdapter(getSampleNotesTT())
        binding.recyclerViewTT.adapter = habitttAdapter

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
        val allCategories = database.categoryDao().getAllCategories().first() // Načteme kategorie
        val categoryNames = allCategories.map { it.categoryName }  // Převedeme na seznam názvů kategorií
        val filteredCategoryNames = categoryNames.filterNotNull().distinct()  // Odfiltrujeme duplicitní názvy kategorií a null
        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, filteredCategoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

        val dialog = AlertDialog.Builder(this)
            .setTitle("New Habit")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = editHabitName.text.toString()
                val categoryName = spinnerCategory.selectedItem.toString()
                addHabitToDatabase(name, categoryName)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }


    private fun addHabitToDatabase(habitName: String, categoryName: String) {
        lifecycleScope.launch {
            val newHabit = Habit(habitName = habitName, categoryName = categoryName)
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