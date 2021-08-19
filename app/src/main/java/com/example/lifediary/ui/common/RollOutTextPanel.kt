package com.example.lifediary.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lifediary.databinding.RollOutTextPanelLayoutBinding

class RollOutTextPanel(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = RollOutTextPanelLayoutBinding.inflate(LayoutInflater.from(context))
    private var isRolledOut = false

    var rolledUpMaxLines: Int = 7
        set(value) {
            field = value
            binding.textView.maxLines = value
        }

    var text: String = ""
        set(value) {
            field = value
            binding.textView.text = text
        }

    companion object {
        private const val ROLLED_OUT_MAX_LINES = 200
    }

    init {
        addView(binding.root)
        setUpTextView()
    }

    private fun setUpTextView() {
        rollUpText()

        binding.container.setOnClickListener {
            isRolledOut = if(isRolledOut) {
                rollUpText()
                false
            } else {
                rollOutText()
                true
            }
        }
    }

    private fun rollUpText() {
        binding.textView.maxLines = rolledUpMaxLines
    }

    private fun rollOutText() {
        binding.textView.maxLines = ROLLED_OUT_MAX_LINES
    }
}