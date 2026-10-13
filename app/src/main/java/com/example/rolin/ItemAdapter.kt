package com.example.rolin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (
    private val lista: MutableList<ItemData>,
    private val onEditClick: (ItemData, Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo1: TextView = view.findViewById(R.id.tvTitulo1)
        val tvTitulo2: TextView = view.findViewById(R.id.tvTitulo2)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val btnEditar: Button = view.findViewById(R.id.btnEditarItem)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = lista[position]
        holder.tvTitulo1.text = item.NombreHabilidad
        holder.tvTitulo2.text = item.CosteHabilidad
        holder.tvDescripcion.text = item.Descripcion

        holder.btnEditar.setOnClickListener { onEditClick(item, position) }
        holder.btnEliminar.setOnClickListener { onDeleteClick(position) }
    }

    override fun getItemCount(): Int = lista.size
}