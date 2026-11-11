package com.example.rolin

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

    private lateinit var dbHelper: DatabaseHelper
    private var userEmail: String = ""
    private var sessionCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habilidades)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        sessionCode = intent.getStringExtra("SESSION_CODE") ?: ""

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

        cargarHabilidades()

        btnGuardar.setOnClickListener {
            val t1 = etTitulo1.text.toString().trim()
            val t2 = etTitulo2.text.toString().trim()
            val desc = etDescripcion.text.toString().trim()

            if (t1.isNotEmpty() && t2.isNotEmpty() && desc.isNotEmpty()) {
                val nuevoItem = ItemData(NombreHabilidad = t1, CosteHabilidad = t2, Descripcion = desc)
                listaItems.add(nuevoItem)
                adapter.notifyItemInserted(listaItems.size - 1)
                limpiarCampos()
            } else {
                Toast.makeText(this, "Completa todos los campos de la habilidad", Toast.LENGTH_SHORT).show()
            }
        }

        btnActualizar.setOnClickListener {
            posModificando?.let { pos ->
                val t1 = etTitulo1.text.toString().trim()
                val t2 = etTitulo2.text.toString().trim()
                val desc = etDescripcion.text.toString().trim()

                if (t1.isNotEmpty() && t2.isNotEmpty() && desc.isNotEmpty()) {
                    listaItems[pos] = ItemData(NombreHabilidad = t1, CosteHabilidad = t2, Descripcion = desc)
                    adapter.notifyItemChanged(pos)
                    limpiarCampos()
                    posModificando = null
                    Toast.makeText(this, "Habilidad modificada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarHabilidades() {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            UserContract.SkillEntry.TABLE_NAME,
            null,
            "${UserContract.SkillEntry.COLUMN_USER_EMAIL} = ? AND ${UserContract.SkillEntry.COLUMN_SESSION_CODE} = ?",
            arrayOf(userEmail, sessionCode),
            null, null, null
        )

        listaItems.clear()
        while (cursor.moveToNext()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.SkillEntry.COLUMN_NOMBRE))
            val coste = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.SkillEntry.COLUMN_COSTE))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.SkillEntry.COLUMN_DESCRIPCION))
            listaItems.add(ItemData(NombreHabilidad = nombre, CosteHabilidad = coste, Descripcion = desc))
        }
        cursor.close()
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        guardarHabilidades()
    }

    private fun guardarHabilidades() {
        val db = dbHelper.writableDatabase
        db.delete(
            UserContract.SkillEntry.TABLE_NAME,
            "${UserContract.SkillEntry.COLUMN_USER_EMAIL} = ? AND ${UserContract.SkillEntry.COLUMN_SESSION_CODE} = ?",
            arrayOf(userEmail, sessionCode)
        )

        for (skill in listaItems) {
            val values = ContentValues().apply {
                put(UserContract.SkillEntry.COLUMN_USER_EMAIL, userEmail)
                put(UserContract.SkillEntry.COLUMN_SESSION_CODE, sessionCode)
                put(UserContract.SkillEntry.COLUMN_NOMBRE, skill.NombreHabilidad)
                put(UserContract.SkillEntry.COLUMN_COSTE, skill.CosteHabilidad)
                put(UserContract.SkillEntry.COLUMN_DESCRIPCION, skill.Descripcion)
            }
            db.insert(UserContract.SkillEntry.TABLE_NAME, null, values)
        }
    }

    private fun limpiarCampos() {
        etTitulo1.text.clear()
        etTitulo2.text.clear()
        etDescripcion.text.clear()
    }
}