package com.example.rolin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.rolin.databinding.FragmentCombatManagerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CombatManagerFragment : Fragment() {

    private var _binding: FragmentCombatManagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DungeonMasterViewModel by activityViewModels()
    private lateinit var enemyAdapter: EnemyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCombatManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        // Inicializamos el adaptador pasando la acción para alterar la vida
        enemyAdapter = EnemyAdapter(emptyList()) { id, delta ->
            viewModel.updateEnemyHp(id, delta)
        }
        binding.rvEnemies.adapter = enemyAdapter
    }

    private fun setupListeners() {
        // Muestra un diálogo emergente interactivo para registrar un nuevo enemigo
        binding.fabAddEnemy.setOnClickListener {
            showAddEnemyDialog()
        }
    }

    private fun observeViewModel() {
        // Escuchamos la lista reactiva de enemigos que guardamos en el ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.enemies.collectLatest { enemyList ->
                enemyAdapter.updateList(enemyList)
            }
        }
    }

    private fun showAddEnemyDialog() {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Nuevo Enemigo en Mesa")

        // Contenedor simple para los campos de texto del diálogo
        val layout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }

        val etName = EditText(context).apply { hint = "Nombre (ej. Trasgo 1)" }
        val etHp = EditText(context).apply {
            hint = "Puntos de Vida Máximos"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        layout.addView(etName)
        layout.addView(etHp)
        builder.setView(layout)

        builder.setPositiveButton("Registrar") { dialog, _ ->
            val name = etName.text.toString()
            val hpText = etHp.text.toString()

            if (name.isNotBlank() && hpText.isNotBlank()) {
                viewModel.addEnemy(name, hpText.toInt())
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}