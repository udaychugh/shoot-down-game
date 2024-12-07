package com.freelab.tech.shootdown

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.freelab.tech.shootdown.databinding.ActivityMainBinding
import com.freelab.tech.shootdown.navigation.NavManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2000)
            NavManager.gotoGameScreenActivity(this@AppStartActivity)
        }
    }
}