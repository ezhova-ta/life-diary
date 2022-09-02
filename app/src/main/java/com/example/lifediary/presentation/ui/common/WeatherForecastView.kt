package com.example.lifediary.presentation.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.lifediary.databinding.WeatherForecastViewBinding

class WeatherForecastView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = WeatherForecastViewBinding.inflate(LayoutInflater.from(context))

    var progressIsVisible: Boolean
        get() = binding.progressView.isVisible
        set(value) {
            binding.progressView.isVisible = value
        }

    var maxTemperature: String? = null
        set(value) {
            field = value
            binding.maxTemperatureView.text = value
        }

    var minTemperature: String? = null
        set(value) {
            field = value
            binding.minTemperatureView.text = value
        }

    var description: String? = null
        set(value) {
            field = value

            with(binding.descriptionView) {
                if (value == null) {
                    isVisible = false
                } else {
                    text = value
                    isVisible = true
                }
            }
        }

    var weatherIconUrl: String? = null
        set(value) {
            field = value

            if(value == null) {
                binding.weatherIcon.setImageURI(null)
            } else {
                Glide.with(context)
                    .load(value)
                    .into(binding.weatherIcon)
            }
        }

    init {
        addView(binding.root)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        binding.container.setOnClickListener(l)
    }
}