package com.example.habittracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitsttListBinding


class HabitTimetableAdapter (private val habitstt: List<HabitTimetable>) : RecyclerView.Adapter<HabitTimetableAdapter.HabitttViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): HabitttViewHolder {
        val binding = HabitsttListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitttViewHolder(binding)
    }

    override fun getItemCount() = habitstt.size

    override fun onBindViewHolder(holder: HabitttViewHolder, position: Int) {
        val habittt = habitstt[position]
        holder.bind(habittt)
    }

    inner class HabitttViewHolder(private val binding: HabitsttListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(habittt: HabitTimetable) {
            binding.habitttId.text = habittt.habitId.toString()
            binding.habitttStatus.text = habittt.habitTimetableStatus.toString()
            binding.habitttHour.text = habittt.HabitTimetableHour.toString()
            binding.habitttDay.text = habittt.habitTimetableDay.toString()
            binding.habitttMonth.text = habittt.habitTimetableMonth.toString()
            binding.habitttYear.text = habittt.habitTimetableYear.toString()

        }
    }

}