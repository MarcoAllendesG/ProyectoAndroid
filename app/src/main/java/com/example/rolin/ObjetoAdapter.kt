package com.example.rolin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolin.databinding.ItemInventarioBinding

class ObjetoAdapter(
    private val lista: List<ObjetoItem>,
    private val onEditarClick: (ObjetoItem) -> Unit,
    private val onEliminarClick: (ObjetoItem, Int) -> Unit
) : RecyclerView.Adapter<ObjetoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemInventarioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInventarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.binding.tvNombre.text = item.nombre
        holder.binding.tvCantidad.text = "Cant: " + item.cantidad.toString()

        holder.binding.btnEditar.setOnClickListener { onEditarClick(item) }

        holder.binding.btnEliminar.setOnClickListener {
            onEliminarClick(item, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = lista.size
}