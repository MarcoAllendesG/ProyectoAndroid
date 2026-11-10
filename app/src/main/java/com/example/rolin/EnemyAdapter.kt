package com.example.rolin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolin.databinding.ItemEnemyBinding

class EnemyAdapter(
    private var enemies: List<EnemyEntity>,
    private val onHpChanged: (String, Int) -> Unit
) : RecyclerView.Adapter<EnemyAdapter.EnemyViewHolder>() {

    inner class EnemyViewHolder(private val binding: ItemEnemyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(enemy: EnemyEntity) {
            binding.tvEnemyName.text = enemy.name
            binding.tvEnemyHp.text = "HP: ${enemy.currentHp} / ${enemy.maxHp}"

            // Configurar barra de progreso porcentual
            binding.pbEnemyHp.max = enemy.maxHp
            binding.pbEnemyHp.progress = enemy.currentHp

            // Listeners para los botones de la mesa física
            binding.btnPlus.setOnClickListener { onHpChanged(enemy.id, 1) }
            binding.btnMinus.setOnClickListener { onHpChanged(enemy.id, -1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnemyViewHolder {
        val binding = ItemEnemyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EnemyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EnemyViewHolder, position: Int) {
        holder.bind(enemies[position])
    }

    override fun getItemCount(): Int = enemies.size

    fun updateList(newEnemies: List<EnemyEntity>) {
        this.enemies = newEnemies
        notifyDataSetChanged() // Notifica el cambio para redibujar la UI
    }
}