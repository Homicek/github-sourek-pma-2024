package com.example.christmasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GiftAdapter(
    private val gifts: MutableList<Gift>,
    private val onGiftLongClick: (Int) -> Unit // Callback pro dlouhé stisknutí
) : RecyclerView.Adapter<GiftAdapter.GiftViewHolder>() {

    inner class GiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val giftName: TextView = itemView.findViewById(R.id.giftName)
        val giftBought: CheckBox = itemView.findViewById(R.id.giftBought)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gift, parent, false)
        return GiftViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiftViewHolder, position: Int) {
        val gift = gifts[position]
        holder.giftName.text = gift.name
        holder.giftBought.isChecked = gift.isBought

        // Dlouhý stisk pro smazání
        holder.itemView.setOnLongClickListener {
            onGiftLongClick(position)
            true
        }

        holder.giftBought.setOnCheckedChangeListener { _, isChecked ->
            gift.isBought = isChecked
        }
    }

    override fun getItemCount(): Int = gifts.size
}