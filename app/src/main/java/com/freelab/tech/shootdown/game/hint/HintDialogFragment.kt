package com.freelab.tech.shootdown.game.hint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.freelab.tech.shootdown.databinding.DialogHintBinding

class HintDialogFragment(
    private val t: Any,
    private val m: Any,
    private val listener: () -> Unit
): DialogFragment() {

    private var _binding: DialogHintBinding? = null
    private val binding get() = _binding!!

    private fun resolvedString(input: Any): String {
        return when (input) {
            is Int -> getString(input)
            is String -> input
            else -> throw IllegalArgumentException("Message must be either String or Int")
        }
    }

    private fun setupInfo() {
        val title = resolvedString(t)
        val message = resolvedString(m)
        binding.apply {
            titleTV.text = title
            messageTV.text = message
        }
    }

    private fun setupClickListener() {
        binding.okayTV.setOnClickListener {
            dismiss()
            listener.invoke()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogHintBinding.inflate(inflater, container, false)

        setupInfo()
        setupClickListener()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}