package com.example.rolin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
/**
 * Calculadora básica integrada para agilizar el conteo de daño,
 * curación, experiencia o monedas durante los encuentros de rol.
 */
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
                inputActual = if (inputActual == "0") digito else inputActual + digito
                tvResultado.text = inputActual
            }
        }

        findViewById<Button>(R.id.btnPunto).setOnClickListener {
            if (!inputActual.contains(".")) {
                inputActual = if (inputActual.isEmpty()) "0." else "$inputActual."
                tvResultado.text = inputActual
            }
        }

        val botonesOperadores = listOf(
            R.id.btnSumar to "+",
            R.id.btnRestar to "-",
            R.id.btnMultiplicar to "*",
            R.id.btnDividir to "/"
        )

        botonesOperadores.forEach { (id, simbolo) ->
            findViewById<Button>(id).setOnClickListener {
                if (inputActual.isNotEmpty()) {
                    primerNumero = inputActual.toDoubleOrNull() ?: 0.0
                    operacion = simbolo
                    inputActual = ""
                } else if (operacion.isNotEmpty()) {
                    operacion = simbolo
                }
            }
        }

        findViewById<Button>(R.id.btnLimpiar).setOnClickListener {
            inputActual = ""
            primerNumero = 0.0
            operacion = ""
            tvResultado.text = "0"
        }

        findViewById<Button>(R.id.btnIgual).setOnClickListener {
            if (operacion.isEmpty() || inputActual.isEmpty()) return@setOnClickListener

            val segundoNumero = inputActual.toDoubleOrNull() ?: 0.0

            if (operacion == "/" && segundoNumero == 0.0) {
                tvResultado.text = "Error"
                inputActual = ""
                operacion = ""
                primerNumero = 0.0
                return@setOnClickListener
            }

            val resultado = when (operacion) {
                "+" -> primerNumero + segundoNumero
                "-" -> primerNumero - segundoNumero
                "*" -> primerNumero * segundoNumero
                "/" -> primerNumero / segundoNumero
                else -> segundoNumero
            }

            val textoFinal = if (resultado % 1.0 == 0.0) {
                resultado.toLong().toString()
            } else {
                resultado.toString()
            }

            tvResultado.text = textoFinal
            inputActual = textoFinal
            operacion = ""
        }
    }
}