package com.example.rolin

import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
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

    private var selectedImageUri: Uri? = null
    private var dialogPreviewImageView: ImageView? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            dialogPreviewImageView?.setImageURI(uri)
        }
    }

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
        enemyAdapter = EnemyAdapter(
            enemies = emptyList(),
            onHpChanged = { id, delta -> viewModel.updateEnemyHp(id, delta) },
            onDeleteEnemy = { id -> confirmDeleteEnemy(id) }
        )
        binding.rvEnemies.adapter = enemyAdapter
    }

    private fun setupListeners() {
        binding.fabAddEnemy.setOnClickListener {
            showAddEnemyDialog()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.enemies.collectLatest { enemyList ->
                enemyAdapter.updateList(enemyList)
            }
        }
    }

    private fun confirmDeleteEnemy(enemyId: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar enemigo")
            .setMessage("¿Deseas quitar esta ficha del combate?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                viewModel.removeEnemy(enemyId)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showAddEnemyDialog() {
        val context = requireContext()
        selectedImageUri = null // Reiniciamos la selección previa

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        // Vista previa de la foto en el diálogo
        val ivPreview = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(180, 180).apply {
                gravity = android.view.Gravity.CENTER_HORIZONTAL
                bottomMargin = 20
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            setImageResource(android.R.drawable.ic_menu_gallery)
        }
        dialogPreviewImageView = ivPreview

        val btnSelectImage = Button(context).apply {
            text = "Seleccionar Foto / Imagen"
            setOnClickListener {
                imagePickerLauncher.launch("image/*")
            }
        }

        val etName = EditText(context).apply {
            hint = "Nombre (ej. Goblin Arquero)"
        }

        val etHp = EditText(context).apply {
            hint = "Puntos de Vida Máximos"
            inputType = InputType.TYPE_CLASS_NUMBER
        }

        layout.addView(ivPreview)
        layout.addView(btnSelectImage)
        layout.addView(etName)
        layout.addView(etHp)

        AlertDialog.Builder(context)
            .setTitle("Nuevo Enemigo")
            .setView(layout)
            .setPositiveButton("Registrar") { dialog, _ ->
                val name = etName.text.toString()
                val hpText = etHp.text.toString()

                if (name.isNotBlank() && hpText.isNotBlank()) {
                    viewModel.addEnemy(
                        name = name,
                        maxHp = hpText.toInt(),
                        imageUri = selectedImageUri?.toString()
                    )
                }
                dialogPreviewImageView = null
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialogPreviewImageView = null
                dialog.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}