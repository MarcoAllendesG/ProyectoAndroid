package com.example.rolin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Clase de datos para representar un enemigo en el registro físico
data class EnemyEntity(
    val id: String,
    val name: String,
    val maxHp: Int,
    var currentHp: Int
)

class DungeonMasterViewModel : ViewModel() {

    // Lista mutable interna de enemigos
    private val _enemies = MutableStateFlow<List<EnemyEntity>>(emptyList())
    val enemies: StateFlow<List<EnemyEntity>> = _enemies

    // Simulación de código de sala/campaña física para compartir
    val campaignCode: String = "CAMP-7742"

    fun addEnemy(name: String, maxHp: Int) {
        val newEnemy = EnemyEntity(
            id = System.currentTimeMillis().toString(),
            name = name,
            maxHp = maxHp,
            currentHp = maxHp
        )
        _enemies.value = _enemies.value + newEnemy
    }

    fun updateEnemyHp(enemyId: String, delta: Int) {
        _enemies.value = _enemies.value.map { enemy ->
            if (enemy.id == enemyId) {
                val newHp = (enemy.currentHp + delta).coerceIn(0, enemy.maxHp)
                enemy.copy(currentHp = newHp)
            } else {
                enemy
            }
        }
    }

    fun removeEnemy(enemyId: String) {
        _enemies.value = _enemies.value.filter { it.id != enemyId }
    }
}