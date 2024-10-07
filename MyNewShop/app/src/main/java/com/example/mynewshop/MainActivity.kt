package com.example.mynewshop

import android.os.Bundle
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

        //title = "Objednávka kola"

        binding.submit.setOnClickListener {

            // načtení ID vybraného radioButtonu z radioGroup
            val rgiPhones = binding.rgiPhones.checkedRadioButtonId

            val iPhoneColor = findViewById<RadioButton>(rgiPhones)

            val white = binding.iphoneWhite.isChecked
            val black = binding.iphoneBlack.isChecked
            val copper = binding.iphoneCopper.isChecked

            /*val objednavkaText = "Souhrn objednávky: " +
                    "${kolo.text}" +
                    (if(vidlice) "; lepší vidlice" else "") +
                    (if(sedlo) "; lepší sedlo" else "") +
                    (if(riditka) "; vytuněná karbonová řidítka" else "")

            binding.tvObjednavka.text = objednavkaText*/

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

    }
}