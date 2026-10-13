package com.example.rolin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HabilidadesAc : AppCompatActivity() {
    private lateinit var etTitulo1: EditText
    private lateinit var etTitulo2: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnActualizar: Button
    private lateinit var recyclerView: RecyclerView

    private val listaItems = mutableListOf<ItemData>()
    private lateinit var adapter: ItemAdapter
    private var posModificando: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habilidades)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etTitulo1 = findViewById(R.id.etNombreHabilidad)
        etTitulo2 = findViewById(R.id.etCosteHabilidad)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnActualizar = findViewById(R.id.btnActualizar)
        recyclerView = findViewById(R.id.recyclerView)

        adapter = ItemAdapter(
            lista = listaItems,
            onEditClick = { item, position ->
                etTitulo1.setText(item.NombreHabilidad)
                etTitulo2.setText(item.CosteHabilidad)
                etDescripcion.setText(item.Descripcion)
                posModificando = position
            },
            onDeleteClick = { position ->
                listaItems.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Botón Guardar (Crear)
        btnGuardar.setOnClickListener {
            val t1 = etTitulo1.text.toString()
            val t2 = etTitulo2.text.toString()
            val desc = etDescripcion.text.toString()

            if (t1.isNotEmpty() && t2.isNotEmpty() && desc.isNotEmpty()) {
                val nuevoItem = ItemData(NombreHabilidad = t1, CosteHabilidad = t2, Descripcion = desc)
                listaItems.add(nuevoItem)
                adapter.notifyItemInserted(listaItems.size - 1)
                limpiarCampos()
            }
        }

        btnActualizar.setOnClickListener {
            posModificando?.let { pos ->
                val t1 = etTitulo1.text.toString()
                val t2 = etTitulo2.text.toString()
                val desc = etDescripcion.text.toString()

                if (t1.isNotEmpty() && t2.isNotEmpty() && desc.isNotEmpty()) {
                    listaItems[pos] = ItemData(NombreHabilidad = t1, CosteHabilidad = t2, Descripcion = desc)
                    adapter.notifyItemChanged(pos)
                    limpiarCampos()
                    posModificando = null
                }
            }
        }
    }

    private fun limpiarCampos() {
        etTitulo1.text.clear()
        etTitulo2.text.clear()
        etDescripcion.text.clear()
    }}