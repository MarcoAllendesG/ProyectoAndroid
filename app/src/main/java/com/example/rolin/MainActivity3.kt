package com.example.rolin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val btnDados = findViewById<Button>(R.id.btnDados)
        val btnCalculadora = findViewById<Button>(R.id.btnCalculadora)
        val btnNotas = findViewById<Button>(R.id.btnNotas)
        val btnInventario = findViewById<Button>(R.id.btnInventario)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            Toast.makeText(this, "¡Personaje Guardado con éxito!", Toast.LENGTH_SHORT).show()
        }

        btnDados.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }

        //btnCalculadora.setOnClickListener {
            //val intent = Intent(this, CalculadoraActivity::class.java)
            //startActivity(intent)
        //}

        //btnNotas.setOnClickListener {
            //val intent = Intent(this, NotasActivity::class.java)
            //startActivity(intent)
        //}

        //btnInventario.setOnClickListener {
            //val intent = Intent(this, InventarioActivity::class.java)
            //startActivity(intent)
        }
    }
