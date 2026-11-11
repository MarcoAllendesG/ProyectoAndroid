package com.example.rolin

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolin.databinding.ItemEnemyBinding

class EnemyAdapter(
    private var enemies: List<EnemyEntity>,
    private val onHpChanged: (String, Int) -> Unit,
    private val onDeleteEnemy: (String) -> Unit
) : RecyclerView.Adapter<EnemyAdapter.EnemyViewHolder>() {

    inner class EnemyViewHolder(private val binding: ItemEnemyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(enemy: EnemyEntity) {
            binding.tvEnemyName.text = enemy.name
            binding.tvEnemyHp.text = "HP: ${enemy.currentHp} / ${enemy.maxHp}"

            binding.pbEnemyHp.max = enemy.maxHp
            binding.pbEnemyHp.progress = enemy.currentHp

            if (!enemy.imageUri.isNullOrEmpty()) {
                binding.ivEnemyAvatar.setImageURI(Uri.parse(enemy.imageUri))
            } else {
                binding.ivEnemyAvatar.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            binding.btnPlus.setOnClickListener { onHpChanged(enemy.id, 1) }
            binding.btnMinus.setOnClickListener { onHpChanged(enemy.id, -1) }
            binding.btnDeleteEnemy.setOnClickListener { onDeleteEnemy(enemy.id) }
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
        notifyDataSetChanged()
    }
}