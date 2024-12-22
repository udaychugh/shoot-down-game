package com.freelab.tech.shootdown.game.levels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freelab.tech.shootdown.databinding.FragmentLevelTwoBinding

class FragmentLevelTwo: Fragment() {

    private var _binding: FragmentLevelTwoBinding? = null
    private val binding get() = _binding!!

    private fun setupObservers() {

    }

    private fun removeObservers() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelTwoBinding.inflate(inflater, container, false)

        setupObservers()

        return binding.root
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
        _binding = null
    }
}