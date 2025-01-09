package com.example.habittracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitsListBinding

class HabitAdapter (private val habits: List<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): HabitViewHolder {
        val binding = HabitsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun getItemCount() = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.bind(habit)
    }

   inner class HabitViewHolder(private val binding: HabitsListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(habit: Habit) {
            binding.habitTitle.text = habit.habitName
            binding.habitCategoryId.text = habit.categoryId.toString()

        }
}

}