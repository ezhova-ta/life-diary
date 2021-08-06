package com.example.lifediary.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lifediary.databinding.CommonCurrentWeatherViewBinding

class CommonCurrentWeatherView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = CommonCurrentWeatherViewBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        binding.container.setOnClickListener(l)
    }
}