package com.freelab.tech.shootdown

import android.animation.ObjectAnimator
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.freelab.tech.shootdown.databinding.ActivityMainBinding
import com.freelab.tech.shootdown.navigation.NavManager

class AppStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppStartViewModel by viewModels()
    private var currentRange: IntRange? = null

    private var loadingMusic: MediaPlayer? = null

    private fun setupObservers() {
        viewModel.progressLiveData.observe(this) { progress ->
            if (progress != -1) {
                binding.loadingProgress.setProgress(progress, true)
                val newRange = when (progress) {
                    in 0..25 -> 0..25
                    in 26..50 -> 26..50
                    in 51..75 -> 51..75
                    in 76..100 -> 76..100
                    else -> null
                }
                if (newRange != currentRange) {
                    currentRange = newRange

                    when (newRange) {
                        0..25 -> animateImageChange(R.drawable.ic_loading_1)
                        26..50 -> animateImageChange(R.drawable.ic_loading_2)
                        51..75 -> animateImageChange(R.drawable.ic_loading_3)
                        76..100 -> animateImageChange(R.drawable.ic_loading_4)
                    }
                }

                return@observe
            }

            NavManager.gotoGameScreenActivity(this@AppStartActivity)
            finish()
        }
    }

    private fun animateImageChange(newImageRes: Int) {
        val fadeIn = ObjectAnimator.ofFloat(binding.bgWallpaperIV, "alpha", 0f, 1f).apply {
            duration = 1000
        }
        binding.bgWallpaperIV.setImageResource(newImageRes)

        fadeIn.start()

    }

    private fun fetchSystemVolume() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (volume == 0) {
            Toast.makeText(this, R.string.volume_up_message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupObservers()
        viewModel.showProgress()

    }

    override fun onStart() {
        super.onStart()
        loadingMusic = MediaPlayer.create(this, R.raw.sound_loading)
        loadingMusic?.start()
        fetchSystemVolume()
    }

    override fun onStop() {
        super.onStop()
        loadingMusic?.stop()
        loadingMusic?.release()
        loadingMusic = null
    }
}