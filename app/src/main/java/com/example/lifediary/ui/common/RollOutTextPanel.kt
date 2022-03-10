package com.example.lifediary.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lifediary.databinding.RollOutTextPanelLayoutBinding

class RollOutTextPanel(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = RollOutTextPanelLayoutBinding.inflate(LayoutInflater.from(context))
    private var isRolledOut = false

    var rolledUpMaxLines: Int = DEFAULT_ROLLED_UP_MAX_LINES
        set(value) {
            field = value
            binding.textView.maxLines = value
        }

    var text: String? = null
        set(value) {
            field = value

            if(value == null) {
                binding.textView.text = ""
            } else {
                binding.textView.text = text
            }
        }

    companion object {
        private const val DEFAULT_ROLLED_UP_MAX_LINES = 7
        private const val DEFAULT_ROLLED_OUT_MAX_LINES = 1000
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
        binding.textView.maxLines = DEFAULT_ROLLED_OUT_MAX_LINES
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        binding.container.setOnLongClickListener(l)
    }
}