package com.example.rolin

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rolin.databinding.FragmentNotesExplorerBinding

class NotesExplorerFragment : Fragment() {

    private var _binding: FragmentNotesExplorerBinding? = null
    private val binding get() = _binding!!

    // Compartimos el ViewModel de la actividad principal (DmAc)
    private val viewModel: DungeonMasterViewModel by activityViewModels()

    // Selector de archivos del sistema (Filtra por PDFs y archivos de texto)
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            handleSelectedFile(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesExplorerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        // Evento para cargar un documento desde el almacenamiento del celular
        binding.btnLoadFile.setOnClickListener {
            // Buscamos cualquier tipo de archivo, idealmente PDF, TXT o DOCX
            filePickerLauncher.launch("*/*")
        }

        // Registro digital en tiempo real: Detecta los cambios mientras escribes
        binding.etSessionNotes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentText = s.toString()
                // Aquí conectarás con tu repositorio o Firebase en la nube para guardar automáticamente
                // ej: viewModel.saveLiveNotes(currentText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleSelectedFile(uri: Uri) {
        // Obtenemos el nombre del archivo de forma simplificada para el registro
        val fileName = uri.lastPathSegment ?: "Archivo de Campaña"
        binding.tvDocumentContent.text = "Documento cargado con éxito:\n$fileName\n\n[Listo para lectura enlazada]"

        Toast.makeText(requireContext(), "Archivo vinculado a la sesión", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}