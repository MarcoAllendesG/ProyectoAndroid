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

    private val viewModel: DungeonMasterViewModel by activityViewModels()

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
        binding.btnLoadFile.setOnClickListener {
            filePickerLauncher.launch("*/*")
        }

        binding.etSessionNotes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentText = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleSelectedFile(uri: Uri) {
        val mimeType = requireContext().contentResolver.getType(uri)

        if (mimeType == "text/plain") {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val content = inputStream?.bufferedReader()?.use { it.readText() }

                binding.tvDocumentContent.text = content ?: "El archivo está vacío."
                binding.tvDocumentContent.movementMethod = android.text.method.ScrollingMovementMethod()
            } catch (e: Exception) {
                binding.tvDocumentContent.text = "Error al leer el archivo: ${e.message}"
            }
        } else if (mimeType == "application/pdf") {
            binding.tvDocumentContent.text = "Archivo PDF detectado. Para verlo aquí, necesitas integrar PdfRenderer o abrirlo externamente."
        } else {
            binding.tvDocumentContent.text = "Formato no soportado para vista previa directa ($mimeType)."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}