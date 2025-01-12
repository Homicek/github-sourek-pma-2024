package com.example.christmasapp

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private val gifts = mutableListOf<Gift>()
    private lateinit var adapter: GiftAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = GiftAdapter(gifts) { position -> // Callback pro dlouhý stisk
            showDeleteConfirmationDialog(position)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addButton: FloatingActionButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            showAddGiftDialog()
        }

        loadGifts()
    }

    private fun showAddGiftDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_gift, null)
        val editGiftName = dialogView.findViewById<EditText>(R.id.editGiftName)

        AlertDialog.Builder(this)
            .setTitle("Add Gift")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = editGiftName.text.toString()
                if (name.isNotEmpty()) {
                    gifts.add(Gift(name, false))
                    adapter.notifyDataSetChanged()
                    saveGifts()
                } else {
                    Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Gift")
            .setMessage("Are you sure you want to delete this gift?")
            .setPositiveButton("Yes") { _, _ ->
                gifts.removeAt(position)
                adapter.notifyDataSetChanged()
                saveGifts()
                Toast.makeText(this, "Gift deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun saveGifts() {
        val sharedPreferences = getSharedPreferences("GiftApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val giftJson = Gson().toJson(gifts)
        editor.putString("gifts", giftJson)
        editor.apply()
    }

    private fun loadGifts() {
        val sharedPreferences = getSharedPreferences("GiftApp", Context.MODE_PRIVATE)
        val giftJson = sharedPreferences.getString("gifts", null)
        if (giftJson != null) {
            val type = object : TypeToken<List<Gift>>() {}.type
            gifts.addAll(Gson().fromJson(giftJson, type))
            adapter.notifyDataSetChanged()
        }
    }
}