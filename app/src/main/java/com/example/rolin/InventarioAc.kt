package com.example.rolin

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rolin.databinding.ActivityInventarioBinding
import com.example.rolin.databinding.DialogObjetoBinding
import java.util.UUID

class InventarioAc : AppCompatActivity() {

    private lateinit var binding: ActivityInventarioBinding
    private val listaObjetos = mutableListOf<ObjetoItem>()
    private lateinit var adapter: ObjetoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ObjetoAdapter(
            listaObjetos,
            onEditarClick = { itemAEditar ->
                mostrarDialogoFormulario(itemAEditar)
            },
            onEliminarClick = { itemAEliminar, posicion ->
                mostrarDialogoConfirmarEliminar(itemAEliminar, posicion)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnAgregar.setOnClickListener {
            mostrarDialogoFormulario(null)
        }
    }

    private fun mostrarDialogoFormulario(item: ObjetoItem?) {
        val dialogBinding = DialogObjetoBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)

        if (item != null) {
            builder.setTitle("Modificar Objeto")
            dialogBinding.etNombre.setText(item.nombre)
            dialogBinding.etCantidad.setText(item.cantidad.toString())
        } else {
            builder.setTitle("Agregar Nuevo Objeto")
        }

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val nombre = dialogBinding.etNombre.text.toString().trim()
            val cantidadStr = dialogBinding.etCantidad.text.toString().trim()

            if (nombre.isEmpty() || cantidadStr.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val cantidad = cantidadStr.toInt()

                if (item != null) {
                    item.nombre = nombre
                    item.cantidad = cantidad
                    adapter.notifyDataSetChanged() // Refresca la lista visualmente
                    Toast.makeText(this, "Objeto modificado", Toast.LENGTH_SHORT).show()
                } else {
                    val nuevoItem = ObjetoItem(UUID.randomUUID().toString(), nombre, cantidad)
                    listaObjetos.add(nuevoItem)
                    adapter.notifyItemInserted(listaObjetos.size - 1)
                }
                dialog.dismiss()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }


    private fun mostrarDialogoConfirmarEliminar(item: ObjetoItem, posicion: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar objeto")
            .setMessage("¿Estás seguro de que deseas eliminar '${item.nombre}' del inventario?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                listaObjetos.removeAt(posicion)
                adapter.notifyItemRemoved(posicion)
                adapter.notifyItemRangeChanged(posicion, listaObjetos.size)

                Toast.makeText(this, "${item.nombre} eliminado", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}