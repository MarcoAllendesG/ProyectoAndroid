package com.example.rolin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rolin.databinding.FragmentCampaignPanelBinding

class CampaignPanelFragment : Fragment() {

    private var _binding: FragmentCampaignPanelBinding? = null
    private val binding get() = _binding!!

    // Recuperamos el mismo ViewModel compartido de la actividad DmAc
    private val viewModel: DungeonMasterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCampaignPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayCampaignData()
        setupListeners()
    }

    private fun displayCampaignData() {
        // Asignamos a la UI el código único de sala definido en el ViewModel
        binding.tvCampaignCode.text = viewModel.campaignCode
    }

    private fun setupListeners() {
        // Utilidad interactiva para copiar el código al portapapeles del celular
        binding.btnCopyCode.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Código RPG", viewModel.campaignCode)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(requireContext(), "¡Código copiado al portapapeles!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}