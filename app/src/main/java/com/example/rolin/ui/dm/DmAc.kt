package com.example.rolin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.example.rolin.databinding.ActivityDmBinding
/**
 * Actividad contenedora del panel del Dungeon Master (DM).
 * Utiliza un sistema de navegación por pestañas (TabLayout y ViewPager2)
 * para alternar entre el control de campaña, el gestor de combate y las notas de una
 * manera más dinámica.
 *
 * @author MarcoAllendesG
 */
class DmAc : AppCompatActivity() {

    private lateinit var binding: ActivityDmBinding
    private val viewModel: DungeonMasterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val incomingCode = intent.getStringExtra("SESSION_CODE")
        if (!incomingCode.isNullOrEmpty()) {
            viewModel.campaignCode = incomingCode
        }

        setupViewPager()
    }
    /**
     * Configura el adaptador de fragmentos del ViewPager2 y lo alinea
     * con las pestañas superiores de navegación.
     */
    private fun setupViewPager() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> CampaignPanelFragment()
                    1 -> CombatManagerFragment()
                    2 -> NotesExplorerFragment()
                    else -> throw IllegalArgumentException("Posición inválida")
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Campaña"
                1 -> "Combate"
                2 -> "Notas y Docs"
                else -> ""
            }
        }.attach()
    }
}