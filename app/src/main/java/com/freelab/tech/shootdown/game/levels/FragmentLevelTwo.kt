package com.freelab.tech.shootdown.game.levels

import android.animation.ValueAnimator
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
import com.freelab.tech.shootdown.databinding.FragmentLevelTwoBinding
import com.freelab.tech.shootdown.game.GameScreenViewModel
import com.freelab.tech.shootdown.game.hint.HintDialogFragment
import com.freelab.tech.shootdown.model.LevelInfo
import com.freelab.tech.shootdown.model.LevelStatus
import com.freelab.tech.shootdown.utils.formatSecondsToMinutesSeconds
import com.freelab.tech.shootdown.utils.generatePlaneImage
import com.freelab.tech.shootdown.utils.isColliding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentLevelTwo: Fragment() {

    companion object {
        private const val DELAY = 3000L
    }

    private var _binding: FragmentLevelTwoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameScreenViewModel by viewModels({ requireActivity() })

    private var planeImage: ImageView? = null
    private var planeAnimator: ValueAnimator? = null

    private var isHintDialogShown = false
    private var isGameEnded = false
    private var planeFired = 0

    private var planeFlyingMusic: MediaPlayer? = null
    private var planeShotMusic: MediaPlayer? = null
    private var fireworksMusic: MediaPlayer? = null

    private fun setupObservers() {
        viewModel.timerLiveData.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let { time ->
                if (time == -1) {
                    endGame()
                } else {
                    binding.timerTV.text = time.formatSecondsToMinutesSeconds()
                }
            }
        }
    }

    private fun removeObservers() {
        viewModel.timerLiveData.removeObservers(viewLifecycleOwner)
    }

    private fun showHintDialog() {
        val dialog = HintDialogFragment(
            R.string.level_two_title,
            R.string.level_two_message
        ) {
            startGame()
        }

        dialog.show(childFragmentManager, "HintDialog")
    }

    private fun startGame() {
        viewModel.startTimer(LevelInfo.LEVEL_TWO.time)
        createPlaneImage()
    }

    private fun endGame() {
        isGameEnded = true
        val message: String = getString(R.string.level_total_score, planeFired)
        val title: String = if (fireworksMusic?.isPlaying == true) {
            getString(LevelStatus.FAILED.msg)
        } else {
            getString(LevelStatus.COMPLETED.msg)
        }

        val dialog = HintDialogFragment(title, message) {
            viewModel.returnToDashboard()
        }

        dialog.show(childFragmentManager, "EndGameDialog")
    }

    private fun createPlaneImage() {
        if (isGameEnded) return
        planeImage = generatePlaneImage().apply {
            setOnClickListener {
                planeFlyingMusic?.stop()
                planeShotMusic?.start()
                setImageResource(R.drawable.ic_level_2_blast)
                planeAnimator?.cancel()
                planeFired++
                binding.scoreTV.text = getString(R.string.level_score, planeFired)
                lifecycleScope.launch {
                    delay(1000)
                    removePlaneImage()
                }
                lifecycleScope.launch {
                    delay(1000)
                    createPlaneImage()
                }
            }
        }
        binding.rootCL.addView(planeImage)

        planeAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = DELAY
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                val parentWidth = binding.rootCL.width
                val newX = parentWidth * (1 - progress)
                planeImage?.translationX = -newX

                if (isColliding(planeImage, binding.tower2IV)) {
                    fireworksMusic?.start()
                    planeFlyingMusic?.stop()
                    viewModel.stopTimer()
                    cancel()
                    removePlaneImage()
                }
            }
            start()
        }
        planeFlyingMusic?.start()
    }

    private fun removePlaneImage() {
        if (planeImage != null) {
            planeAnimator?.cancel()
            planeAnimator?.end()
            binding.rootCL.removeView(planeImage)
            planeImage = null
            planeAnimator = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelTwoBinding.inflate(inflater, container, false)

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

        planeFlyingMusic = MediaPlayer.create(requireContext(), R.raw.sound_level_2_music_plane)
        planeShotMusic = MediaPlayer.create(requireContext(), R.raw.sound_level_2_music_blast)
        fireworksMusic = MediaPlayer.create(requireContext(), R.raw.sound_level_2_fireworks)

    }

    override fun onStop() {
        super.onStop()

        planeFlyingMusic?.release()
        planeShotMusic?.release()
        fireworksMusic?.release()

        planeFlyingMusic = null
        planeShotMusic = null
        fireworksMusic = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
        _binding = null
    }
}