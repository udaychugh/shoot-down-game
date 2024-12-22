package com.freelab.tech.shootdown.game.levels

import android.media.MediaPlayer
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
import com.freelab.tech.shootdown.game.hint.HintDialogFragment
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
    private var hasMonsterShot = false
    private var monsterFiredCount = 0

    private var gunShotMusic: MediaPlayer? = null
    private var zombieMusic: MediaPlayer? = null

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
        val dialog = HintDialogFragment(R.string.level_one_title, R.string.level_one_message) {
            startGame()
        }

        dialog.show(childFragmentManager, "HintDialog")
    }

    private fun startGame() {
        viewModel.startTimer(LevelInfo.LEVEL_ONE.time)
        showNewMonster()
    }

    private fun endGame() {
        val message: String = getString(R.string.level_total_score, monsterFiredCount)
        val title: String = if (monsterFiredCount >= LevelInfo.LEVEL_ONE.score) {
            getString(LevelStatus.COMPLETED.msg)
        } else {
            getString(LevelStatus.FAILED.msg)
        }
        val dialog = HintDialogFragment(title, message) {
            viewModel.returnToDashboard()
        }

        dialog.show(childFragmentManager, "EndGameDialog")
    }

    private fun playGunShotSound() {
        gunShotMusic?.start()
    }

    private fun playMonsterAppearSound() {
        zombieMusic?.start()
    }

    private fun showNewMonster() {
        lifecycleScope.launch(Dispatchers.IO) {
            val monsterTurn = Random.nextInt(1, 11)
            delay(DELAY)
            hasMonsterShot = false
            playMonsterAppearSound()
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
            if (hasMonsterShot) {
                return@launch
            }
            hasMonsterShot = true
            monsterFiredCount++
            playGunShotSound()
            withContext(Dispatchers.Main) {
                binding.scoreTV.text = getString(R.string.level_score, monsterFiredCount)
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
                monster2 to bloodStrain2,
                monster3 to bloodStrain3,
                monster4 to bloodStrain4,
                monster5 to bloodStrain5,
                monster6 to bloodStrain6,
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

        binding.scoreTV.text = getString(R.string.level_score, 0)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (!isHintDialogShown) {
            isHintDialogShown = true
            showHintDialog()
        }

        gunShotMusic = MediaPlayer.create(requireContext(), R.raw.sound_level_1_gunshot)
        zombieMusic = MediaPlayer.create(requireContext(), R.raw.sound_level_1_zombie_laugh)
    }

    override fun onStop() {
        super.onStop()
        gunShotMusic = null
        zombieMusic = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
        _binding = null
    }

}