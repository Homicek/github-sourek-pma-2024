package com.example.mynewshop

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynewshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()


        // binding settings
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "iPhone 16 pro new order"


        binding.submit.setOnClickListener {
            // načtení ID vybraného radioButtonu z radioGroup
            val rgiPhones = binding.rgiPhones.checkedRadioButtonId

            val iPhoneColor = findViewById<RadioButton>(rgiPhones)

            val leatherCase = binding.leatherCase.isChecked
            val headphones = binding.headphones.isChecked
            val protectiveGlass = binding.protectiveGlass.isChecked



            val objednavkaText = "Order summary:\n"+
                    "Color: ${iPhoneColor.text}" +
                    (if(leatherCase) ", additional protective leather case " else "") +
                    (if(headphones) ", additional Airpods pro 2" else "") +
                    (if(protectiveGlass) ", apply a protective gorilla glass" else "") + "."

            binding.summaryText.text = objednavkaText

        }

        // Změna obrázku v závislosti na vybraném radioButtonu

        binding.iphoneWhite.setOnClickListener {
            binding.iphoneImage.setImageResource(R.drawable.iphonewhite)
        }

        binding.iphoneBlack.setOnClickListener {
            binding.iphoneImage.setImageResource(R.drawable.iphoneblack)
        }

        binding.iphoneCopper.setOnClickListener {
            binding.iphoneImage.setImageResource(R.drawable.iphonecopper)
        }

        val btnReset = findViewById<Button>(R.id.reset)

        btnReset.setOnClickListener {
            binding.headphones.isChecked = false
            binding.leatherCase.isChecked = false
            binding.protectiveGlass.isChecked = false
            binding.summaryText.text="Order summary:"

        }

    }
}