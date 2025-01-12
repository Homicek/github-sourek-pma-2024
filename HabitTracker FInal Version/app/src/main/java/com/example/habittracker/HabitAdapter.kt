package com.example.habittracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.Habit

class HabitAdapter(
    private val onCompletedChange: (Habit, Boolean) -> Unit,
    private val onDeleteClick: (Habit) -> Unit // Přidán parametr pro mazání

) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    private var habits: List<Habit> = listOf()

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_habit_name)
        val tvCategory: TextView = itemView.findViewById(R.id.tv_habit_category)
        val tvPriority: TextView = itemView.findViewById(R.id.tv_habit_priority)
        val tvDateTime: TextView = itemView.findViewById(R.id.tv_habit_date_time)
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cb_habit_completed)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete) // Tlačítko pro odstranění

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.tvName.text = habit.name
        holder.tvCategory.text = "Category: ${habit.category}"
        holder.tvPriority.text = "Priority: ${habit.priority}"
        holder.tvDateTime.text = habit.dateTime ?: "No Date/Time"
        holder.cbCompleted.isChecked = habit.isCompleted

        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            onCompletedChange(habit, isChecked)
        }


        holder.btnDelete.setOnClickListener {
            onDeleteClick(habit)
        }
    }

    override fun getItemCount(): Int = habits.size

    fun setHabits(habits: List<Habit>) {
        this.habits = habits
        notifyDataSetChanged()
    }
}


