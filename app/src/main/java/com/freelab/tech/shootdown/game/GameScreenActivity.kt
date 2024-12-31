package com.freelab.tech.shootdown.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.freelab.tech.shootdown.databinding.ActivityGameScreenBinding
import com.freelab.tech.shootdown.game.levels.FragmentDashboard
import com.freelab.tech.shootdown.game.levels.FragmentLevelOne
import com.freelab.tech.shootdown.game.levels.FragmentLevelTwo
import com.freelab.tech.shootdown.model.LevelInfo
import kotlinx.coroutines.launch

class GameScreenActivity : AppCompatActivity() {

    companion object {
        private const val DASHBOARD = "dashboard"
        private const val LEVEL_ONE = "level_one"
        private const val LEVEL_TWO = "level_two"
        private const val LEVEL_THREE = "level_three"
        private const val LEVEL_FOUR = "level_four"
        private const val LEVEL_FIVE = "level_five"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, GameScreenActivity::class.java)
        }
    }

    private lateinit var binding: ActivityGameScreenBinding

    private val viewModel: GameScreenViewModel by viewModels()

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.currentFragmentState.collect{ level ->
                when(level) {
                    LevelInfo.DASHBOARD -> {
                        showFragment(DASHBOARD)
                    }
                    LevelInfo.LEVEL_ONE -> {
                        showFragment(LEVEL_ONE)
                    }
                    LevelInfo.LEVEL_TWO -> {
                        showFragment(LEVEL_TWO)
                    }
                    LevelInfo.LEVEL_THREE -> {}
                    LevelInfo.LEVEL_FOUR -> {}
                    LevelInfo.LEVEL_FIVE -> {}
                }
            }
        }
    }

    private fun showFragment(level: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = when (level) {
            DASHBOARD -> FragmentDashboard()
            LEVEL_ONE -> FragmentLevelOne()
            LEVEL_TWO -> FragmentLevelTwo()
            else -> FragmentLevelOne()
        }

        transaction.replace(binding.fcv.id, fragment).commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
    }
}