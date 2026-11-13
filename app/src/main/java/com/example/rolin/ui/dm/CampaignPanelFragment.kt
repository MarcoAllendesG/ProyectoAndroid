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
/**
 * Fragmento encargado de mostrar el panel general del dungeon master,
 * desplegando el código único de la sala y permitiendo copiarlo al portapapeles.
 */
class CampaignPanelFragment : Fragment() {

    private var _binding: FragmentCampaignPanelBinding? = null
    private val binding get() = _binding!!

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
    /**
     * Asigna el código de campaña activo desde el ViewModel hacia la interfaz visual.
     */
    private fun displayCampaignData() {
        binding.tvCampaignCode.text = viewModel.campaignCode
    }

    private fun setupListeners() {
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