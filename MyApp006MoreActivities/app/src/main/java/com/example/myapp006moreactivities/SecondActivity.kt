package com.example.myapp006moreactivities

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        title = "Druhá aktivita"

        val twInfo = findViewById<TextView>(R.id.twInfo)
        val etSurname = findViewById<EditText>(R.id.etSurname)

        //Načtení dat z intentu
        val nickname = intent.getStringExtra("NICK_NAME")
        twInfo.text = "Data z první aktivty: $nickname"





        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val btnThird = findViewById<Button>(R.id.btnThird)
        btnThird.setOnClickListener{
            val surname = etSurname.text.toString() // získáme text z edit text pole
            val intent = Intent(this, third_activity::class.java)
            intent.putExtra("SURNAME",surname)
            startActivity(intent)
        }



    }
}