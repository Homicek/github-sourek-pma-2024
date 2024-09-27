package com.example.myapplication002

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        setContentView(R.layout.activity_main)

         /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
         }
         */

        val etName = findViewById<EditText>(R.id.etName)
        val etSurname = findViewById<EditText>(R.id.etSurname)
        val etTown = findViewById<EditText>(R.id.etTown)
        val etAge = findViewById<EditText>(R.id.etAge)
        val twInformation = findViewById<TextView>(R.id.twInformation)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnDelete = findViewById<Button>(R.id.btnVymazat)

        //Nastavení pro tlačítko odeslat
        btnSend.setOnClickListener {
            val name = etName.text.toString()
            val surname = etSurname.text.toString()
            val town = etTown.text.toString()
            val age = etAge.text.toString()

            // Zobrazení textu v TextView
            val formattedText = "Jmenuji se $name $surname, jsem z $town a je mi $age let."
            twInformation.text = formattedText
        }

        btnDelete.setOnClickListener {
            etName.text.clear()
            etSurname.text.clear()
            etTown.text.clear()
            etAge.text.clear()

            twInformation.text = ""
        }




    }
}