package com.freelab.tech.shootdown.game.levels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.freelab.tech.shootdown.R
import com.freelab.tech.shootdown.databinding.FragmentDashboardBinding
import com.freelab.tech.shootdown.game.GameScreenViewModel
import com.freelab.tech.shootdown.game.adapters.LevelsAdapter
import com.freelab.tech.shootdown.model.LevelInfo

class FragmentDashboard:Fragment() {

    companion object {
        private const val LEVEL_ONE = 0
        private const val LEVEL_TWO = 1
        private const val LEVEL_THREE = 2
        private const val LEVEL_FOUR = 3
        private const val LEVEL_FIVE = 4
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameScreenViewModel by viewModels({ requireActivity() })

    private fun setupCLickListener() {
        binding.apply {
            playBtn.setOnClickListener {
                playBtn.visibility = View.GONE
                levelRV.visibility = View.VISIBLE
                levelsTV.visibility = View.VISIBLE
            }
        }
    }

    private fun setupLevelsRV() {
        val levelsList = arrayListOf(
            getString(R.string.level_one_title),
            getString(R.string.level_two_title),
            getString(R.string.level_three_title),
            getString(R.string.level_four_title),
            getString(R.string.level_five_title)
        )
        binding.levelRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.levelRV.adapter = LevelsAdapter(levelsList) { position ->
            when(position) {
                LEVEL_ONE -> {
                    viewModel.startLevel(LevelInfo.LEVEL_ONE)
                }
                LEVEL_TWO -> {
                    viewModel.startLevel(LevelInfo.LEVEL_TWO)
                }
                LEVEL_THREE -> {
                    Toast.makeText(requireContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
                }
                LEVEL_FOUR -> {
                    Toast.makeText(requireContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
                }
                LEVEL_FIVE -> {
                    Toast.makeText(requireContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        setupCLickListener()
        setupLevelsRV()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}