package com.freelab.tech.shootdown.game.levels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.freelab.tech.shootdown.R
import com.freelab.tech.shootdown.databinding.FragmentLevelOneBinding
import com.freelab.tech.shootdown.game.GameScreenViewModel
import com.freelab.tech.shootdown.game.hint.HintDialog
import com.freelab.tech.shootdown.model.LevelInfo
import com.freelab.tech.shootdown.model.LevelStatus
import com.freelab.tech.shootdown.utils.formatSecondsToMinutesSeconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class FragmentLevelOne: Fragment() {

    companion object {
        private const val DELAY = 1000L
    }

    private var _binding: FragmentLevelOneBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameScreenViewModel by viewModels({ requireActivity() })

    private var isHintDialogShown = false
    private var monsterFiredCount = 0

    private fun setupObservers() {
        viewModel.timerLiveData.observe(viewLifecycleOwner) { time ->
            if (time == -1) {
                endGame()
            } else {
                binding.timerTV.text = time.formatSecondsToMinutesSeconds()
            }
        }
    }

    private fun removeObservers() {
        viewModel.timerLiveData.removeObservers(viewLifecycleOwner)
    }

    private fun showHintDialog() {
        HintDialog.showHintDialog(requireContext(), R.string.level_one_title, R.string.level_one_message) {
            startGame()
        }
    }

    private fun startGame() {
        viewModel.startTimer(60_000L)
        showNewMonster()
    }

    private fun endGame() {
        val message: String = getString(R.string.level_score, monsterFiredCount)
        val title: String = if (monsterFiredCount >= LevelInfo.LEVEL_ONE.score) {
            getString(LevelStatus.COMPLETED.msg)
        } else {
            getString(LevelStatus.FAILED.msg)
        }
        HintDialog.showHintDialog(requireContext(), title, message) {

        }
    }

    private fun playGunShotSound() {

    }

    private fun showNewMonster() {
        lifecycleScope.launch(Dispatchers.IO) {
            val monsterTurn = Random.nextInt(1, 11)
            delay(DELAY)
            withContext(Dispatchers.Main) {
                when(monsterTurn) {
                    1 -> binding.monster1.visibility = View.VISIBLE
                    2 -> binding.monster1.visibility = View.VISIBLE
                    3 -> binding.monster1.visibility = View.VISIBLE
                    4 -> binding.monster4.visibility = View.VISIBLE
                    5 -> binding.monster5.visibility = View.VISIBLE
                    6 -> binding.monster6.visibility = View.VISIBLE
                    7 -> binding.monster7.visibility = View.VISIBLE
                    8 -> binding.monster8.visibility = View.VISIBLE
                    9 -> binding.monster9.visibility = View.VISIBLE
                    10 -> binding.monster10.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun monsterFired(monster: ImageView, blood: ImageView) {
        lifecycleScope.launch(Dispatchers.IO) {
            monsterFiredCount++
            playGunShotSound()
            withContext(Dispatchers.Main) {
                blood.visibility = View.VISIBLE
            }
            delay(DELAY)
            withContext(Dispatchers.Main) {
                blood.visibility = View.GONE
                monster.visibility = View.GONE
            }
            showNewMonster()
        }
    }

    private fun setupCLickListener() {
        binding.apply {
            val monsters = listOf(
                monster1 to bloodStrain1,
                monster2 to bloodStrain1,
                monster3 to bloodStrain1,
                monster4 to bloodStrain1,
                monster5 to bloodStrain1,
                monster6 to bloodStrain1,
                monster7 to bloodStrain7,
                monster8 to bloodStrain8,
                monster9 to bloodStrain9,
                monster10 to bloodStrain10
            )
            monsters.forEach { (monster, bloodStrain) ->
                monster.setOnClickListener {
                    monsterFired(monster, bloodStrain)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelOneBinding.inflate(inflater, container, false)

        setupCLickListener()
        setupObservers()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (!isHintDialogShown) {
            isHintDialogShown = true
            showHintDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
        _binding = null
    }

}