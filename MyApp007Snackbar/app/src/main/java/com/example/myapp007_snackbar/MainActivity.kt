package com.example.myapp007_snackbar

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp007_snackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inicialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowToast.setOnClickListener{

            val toast = Toast.makeText(this, "Nazdar - mám hlad", Toast.LENGTH_SHORT)
            toast.show()
        }

        //nastavení pro SnackBar
        binding.btnShowSnackbar.setOnClickListener{
            Snackbar.make(binding.root, "Já jsem SNACKBAR",Snackbar.LENGTH_SHORT)
                .setDuration(7000)
                .setBackgroundTint(Color.parseColor("#FF35AA"))
                .setTextColor(Color.BLACK)
                .setActionTextColor(Color.WHITE)

                .setAction("Zavřít"){Toast.makeText(this,"zavírám SNACKBAR",Toast.LENGTH_SHORT).show()

                }
                .show()
        }

    }
}