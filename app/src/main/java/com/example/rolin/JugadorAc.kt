package com.example.rolin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JugadorAc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugador)

        val btnDados = findViewById<Button>(R.id.btnDados)
        val btnCalculadora = findViewById<Button>(R.id.btnCalculadora)
        val btnNotas = findViewById<Button>(R.id.btnNotas)
        val btnInventario = findViewById<Button>(R.id.btnInventario)
        val btnHabilidades = findViewById<Button>(R.id.btnHabilidades)

        btnHabilidades.setOnClickListener {
            val intent = Intent(this, HabilidadesAc::class.java)
            startActivity(intent)
        }

        btnDados.setOnClickListener {
            val intent = Intent(this, DadoAc::class.java)
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

        btnInventario.setOnClickListener {
            val intent = Intent(this, InventarioAc::class.java)
            startActivity(intent)
        }
    }
}