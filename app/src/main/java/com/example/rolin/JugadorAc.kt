package com.example.rolin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class JugadorAc : AppCompatActivity() {

    private lateinit var imgAvatarPersonaje: ImageView

    // Declaración de variables para Vida, Maná y Aguante
    private lateinit var etVidaActual: EditText
    private lateinit var etVidaMax: EditText
    private lateinit var etManaActual: EditText
    private lateinit var etManaMax: EditText
    private lateinit var etAguanteActual: EditText
    private lateinit var etAguanteMax: EditText

    // Declaración de variables para las Barras de Progreso
    private lateinit var pbVida: ProgressBar
    private lateinit var pbMana: ProgressBar
    private lateinit var pbAguante: ProgressBar

    private val tomarFotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            imgAvatarPersonaje.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private val solicitarPermisoLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { esConcedido: Boolean ->
        if (esConcedido) {
            tomarFotoLauncher.launch(null)
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugador)

        imgAvatarPersonaje = findViewById(R.id.imgAvatarPersonaje)

        // Inicializar Inputs de Estado
        etVidaActual = findViewById(R.id.etVidaActual)
        etVidaMax = findViewById(R.id.etVidaMax)
        etManaActual = findViewById(R.id.etManaActual)
        etManaMax = findViewById(R.id.etManaMax)
        etAguanteActual = findViewById(R.id.etAguanteActual)
        etAguanteMax = findViewById(R.id.etAguanteMax)

        // Inicializar Barras de Progreso
        pbVida = findViewById(R.id.pbVida)
        pbMana = findViewById(R.id.pbMana)
        pbAguante = findViewById(R.id.pbAguante)

        // Vincular e implementar la lógica de actualización en tiempo real
        vincularBarraYValidar(etVidaActual, etVidaMax, pbVida, "Vida")
        vincularBarraYValidar(etManaActual, etManaMax, pbMana, "Maná")
        vincularBarraYValidar(etAguanteActual, etAguanteMax, pbAguante, "Aguante")

        val btnDados = findViewById<Button>(R.id.btnDados)
        val btnCalculadora = findViewById<Button>(R.id.btnCalculadora)
        val btnNotas = findViewById<Button>(R.id.btnNotas)
        val btnInventario = findViewById<Button>(R.id.btnInventario)
        val btnHabilidades = findViewById<Button>(R.id.btnHabilidades)

        imgAvatarPersonaje.setOnClickListener {
            verificarPermisoYTomarFoto()
        }

        btnHabilidades.setOnClickListener {
            val intent = Intent(this, HabilidadesAc::class.java)
            startActivity(intent)
        }

        btnDados.setOnClickListener {
            val intent = Intent(this, DadoAc::class.java)
            startActivity(intent)
        }

        btnInventario.setOnClickListener {
            val intent = Intent(this, InventarioAc::class.java)
            startActivity(intent)
        }

        btnCalculadora.setOnClickListener {
            val intent = Intent(this, CalculadoraAc::class.java)
            startActivity(intent)
        }

        btnNotas.setOnClickListener {
            val intent = Intent(this, NotasAc::class.java)
            startActivity(intent)
        }

    }

    private fun verificarPermisoYTomarFoto() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                tomarFotoLauncher.launch(null)
            }
            else -> {
                solicitarPermisoLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    /**
     * Valida que el valor ingresado no supere el máximo asignado
     * y actualiza de manera proporcional las barras de progreso.
     */
    private fun vincularBarraYValidar(etActual: EditText, etMax: EditText, progressBar: ProgressBar, nombreAtributo: String) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val actualStr = etActual.text.toString()
                val maxStr = etMax.text.toString()

                if (actualStr.isNotEmpty() && maxStr.isNotEmpty()) {
                    var actual = actualStr.toIntOrNull() ?: 0
                    val max = maxStr.toIntOrNull() ?: 1 // Evitamos la división por 0

                    if (actual > max) {
                        actual = max
                        etActual.setText(max.toString())
                        etActual.setSelection(etActual.text.length)
                        Toast.makeText(this@JugadorAc, "$nombreAtributo no puede superar el máximo", Toast.LENGTH_SHORT).show()
                    }

                    progressBar.max = max
                    progressBar.progress = actual
                } else {
                    progressBar.progress = 0
                }
            }
        }

        etActual.addTextChangedListener(textWatcher)
        etMax.addTextChangedListener(textWatcher)
    }
}