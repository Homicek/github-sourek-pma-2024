package com.example.habittracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.Habit
import com.example.habittracker.HabitDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddHabitActivity : AppCompatActivity() {

    private lateinit var database: HabitDatabase
    private var selectedDate: String? = null // Volitelné datum
    private var selectedTime: String? = null // Volitelný čas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        // Inicializace databáze
        database = HabitDatabase.getDatabase(this)

        // UI komponenty
        val etHabitName = findViewById<EditText>(R.id.et_habit_name)
        val spHabitCategory = findViewById<Spinner>(R.id.sp_habit_category)
        val spHabitPriority = findViewById<Spinner>(R.id.sp_habit_priority)
        val btnSetDate = findViewById<Button>(R.id.btn_set_date)
        val tvSelectedDate = findViewById<TextView>(R.id.tv_selected_date)
        val btnSetTime = findViewById<Button>(R.id.btn_set_time)
        val tvSelectedTime = findViewById<TextView>(R.id.tv_selected_time)
        val btnSaveHabit = findViewById<Button>(R.id.btn_save_habit)

        // Nastavení data
        btnSetDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                tvSelectedDate.text = "Selected date: $selectedDate"
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Nastavení času
        btnSetTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { _, hour, minute ->
                selectedTime = String.format("%02d:%02d", hour, minute)
                tvSelectedTime.text = "Selected time: $selectedTime"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        // Uložení návyku
        btnSaveHabit.setOnClickListener {
            val name = etHabitName.text.toString()
            val category = spHabitCategory.selectedItem.toString()
            val priority = spHabitPriority.selectedItem.toString()

            // Validace názvu návyku
            if (name.isNotEmpty()) {
                // Kombinace data a času (pokud jsou nastaveny)
                val dateTime = when {
                    selectedDate != null && selectedTime != null -> "$selectedDate $selectedTime"
                    selectedDate != null -> selectedDate
                    else -> null
                }

                val habit = Habit(
                    name = name,
                    category = category,
                    priority = priority,
                    dateTime = dateTime,
                    isCompleted = false
                )

                CoroutineScope(Dispatchers.IO).launch {
                    database.habitDao().insertHabit(habit)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddHabitActivity, "Habit saved!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a habit name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
