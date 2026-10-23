package com.example.rolin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotasAc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val notaTexto = etNote.text.toString()
            if (notaTexto.isNotEmpty()) {
                // Aquí puedes guardar en SharedPreferences o SQLite / Room
                Toast.makeText(this, "Nota guardada: $notaTexto", Toast.LENGTH_SHORT).show()
                finish() // Cierra la activity y vuelve atrás
            } else {
                Toast.makeText(this, "Escribe algo primero", Toast.LENGTH_SHORT).show()
            }
        }
    }
}