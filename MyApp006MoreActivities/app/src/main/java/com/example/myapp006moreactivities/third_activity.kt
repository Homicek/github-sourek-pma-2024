package com.example.myapp006moreactivities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class third_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_third)

        val surname = intent.getStringExtra("SURNAME")
        val thirdActivityData = findViewById<TextView>(R.id.thirdActivityData)

        thirdActivityData.text = "Data ze druhé aktivty: $surname"


    }
}