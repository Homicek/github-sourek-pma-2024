package com.example.myownapp03

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val tvVysledek = findViewById<TextView>(R.id.textVysledek)
        val tvSongName = findViewById<TextView>(R.id.songName)
        val tvSongLenght = findViewById<TextView>(R.id.songLenght)
        val tvGenre = findViewById<TextView>(R.id.genre)
        val tvSongArtist = findViewById<TextView>(R.id.songArtist)
        val tvLyrics = findViewById<TextView>(R.id.songLyrics)

        btnSubmit.setOnClickListener {
            val SongName = tvSongName.text.toString()
            val SongLenght = tvSongLenght.text.toString()
            val Genre = tvGenre.text.toString()
            val Lyrics = tvLyrics.text.toString()
            val Artist = tvSongArtist.text.toString()

            val formatedText = "Jméno písničky je $SongName od Interpreta $Artist, je dlouhá $SongLenght a je vytvořená v žánru $Genre. \n\n\nOblíbená část písničky:\n\n$Lyrics"
            tvVysledek.text = formatedText
        }




    }
}