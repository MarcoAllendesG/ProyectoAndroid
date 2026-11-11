package com.example.rolin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SalaAc : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var etCode: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnJoin: Button

    private lateinit var dbHelper: DatabaseHelper
    private var userEmail: String = "Aventurero"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sala)

        dbHelper = DatabaseHelper(this)

        tvWelcome = findViewById(R.id.textView)
        etCode = findViewById(R.id.textView2)
        btnCreate = findViewById(R.id.btnCreate)
        btnJoin = findViewById(R.id.btnJoin)

        userEmail = intent.getStringExtra("USER_EMAIL") ?: "Aventurero"
        tvWelcome.text = "Bienvenido\n$userEmail"

        setupListeners()
    }

    private fun setupListeners() {
        btnCreate.setOnClickListener {
            val randomNum = Random.nextInt(1000, 9999)
            val generatedCode = "CAMP-$randomNum"

            if (dbHelper.createSession(generatedCode, userEmail)) {
                Toast.makeText(this, "Sala creada: $generatedCode", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DmAc::class.java).apply {
                    putExtra("SESSION_CODE", generatedCode)
                    putExtra("USER_EMAIL", userEmail)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al crear la sala en BD", Toast.LENGTH_LONG).show()
            }
        }

        btnJoin.setOnClickListener {
            val inputCode = etCode.text.toString().trim().uppercase()

            if (inputCode.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un código válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.checkSessionExists(inputCode)) {
                Toast.makeText(this, "Uniéndose a $inputCode...", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, JugadorAc::class.java).apply {
                    putExtra("SESSION_CODE", inputCode)
                    putExtra("USER_EMAIL", userEmail)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Código inválido o la sesión no existe", Toast.LENGTH_LONG).show()
            }
        }
    }
}