package com.example.rolin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class JugadorAc : AppCompatActivity() {

    private lateinit var imgAvatarPersonaje: ImageView

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
            Toast.makeText(this, "Proximamente...", Toast.LENGTH_SHORT).show()
        }

        btnNotas.setOnClickListener {
            Toast.makeText(this, "Proximamente...", Toast.LENGTH_SHORT).show()
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
}