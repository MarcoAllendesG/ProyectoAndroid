package com.example.rolin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
/**
 * Representa la entidad de un enemigo o monstruo dentro del registro de combate.
 *
 * @property id Identificador único de la ficha del enemigo.
 * @property name Nombre descriptivo del enemigo en mesa.
 * @property maxHp Puntos de vida máximos iniciales.
 * @property currentHp Puntos de vida actuales durante el encuentro.
 * @property imageUri Ruta local opcional de la imagen o avatar asignado.
 */
data class EnemyEntity(
    val id: String,
    val name: String,
    val maxHp: Int,
    var currentHp: Int,
    val imageUri: String? = null
)
/**
 * ViewModel compartido entre los fragmentos del Dungeon Master.
 * Mantiene el estado reactivo de la sesión, el código de sala y la lista de enemigos
 * en combate sobreviviendo a cambios de configuración.
 */
class DungeonMasterViewModel : ViewModel() {

    private val _enemies = MutableStateFlow<List<EnemyEntity>>(emptyList())
    val enemies: StateFlow<List<EnemyEntity>> = _enemies

    var campaignCode: String = "CAMP-7742"
    /**
     * Crea y añade una nueva ficha de enemigo a la lista activa del combate para tener una
     * idea de su estado actual.
     *
     * @param name Nombre del enemigo.
     * @param maxHp Puntos de vida máximos con los que inicia.
     * @param imageUri Ruta opcional de la fotografía seleccionada desde la galería.
     */
    fun addEnemy(name: String, maxHp: Int, imageUri: String? = null) {
        val newEnemy = EnemyEntity(
            id = System.currentTimeMillis().toString(),
            name = name,
            maxHp = maxHp,
            currentHp = maxHp,
            imageUri = imageUri
        )
        _enemies.value = _enemies.value + newEnemy
    }
    /**
     * Modifica los puntos de vida actuales de un enemigo específico siguiendo lo ocurrido
     * durante el combate que se este haciendo, asegurando
     * que el valor resultante se mantenga entre 0 y su vida máxima.
     *
     * @param enemyId Identificador único del enemigo a modificar.
     * @param delta Cantidad de vida a sumar (positivo) o restar (negativo).
     */
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
    /**
     * Elimina permanentemente la ficha de un enemigo para que ya no se muestre.
     *
     * @param enemyId Identificador único del enemigo a remover.
     */
    fun removeEnemy(enemyId: String) {
        _enemies.value = _enemies.value.filter { it.id != enemyId }
    }
}