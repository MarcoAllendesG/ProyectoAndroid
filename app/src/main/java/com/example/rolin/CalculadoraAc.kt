package com.example.rolin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CalculadoraAc : AppCompatActivity() {

    private lateinit var tvResultado: TextView
    private var inputActual = ""
    private var operacion = ""
    private var primerNumero = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        tvResultado = findViewById(R.id.tvResultado)

        val botonesNumeros = listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9"
        )

        botonesNumeros.forEach { (id, digito) ->
            findViewById<Button>(id).setOnClickListener {
                inputActual += digito
                tvResultado.text = inputActual
            }
        }

        findViewById<Button>(R.id.btnLimpiar).setOnClickListener {
            inputActual = ""
            primerNumero = 0.0
            operacion = ""
            tvResultado.text = "0"
        }

        findViewById<Button>(R.id.btnIgual).setOnClickListener {
            val segundoNumero = inputActual.toDoubleOrNull() ?: 0.0
            val resultado = when (operacion) {
                "+" -> primerNumero + segundoNumero
                "-" -> primerNumero - segundoNumero
                "*" -> primerNumero * segundoNumero
                "/" -> if (segundoNumero != 0.0) primerNumero / segundoNumero else 0.0
                else -> segundoNumero
            }
            tvResultado.text = resultado.toString()
            inputActual = resultado.toString()
        }
    }
}