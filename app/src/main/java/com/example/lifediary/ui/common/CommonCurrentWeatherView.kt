package com.example.lifediary.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lifediary.databinding.CommonCurrentWeatherViewBinding

class CommonCurrentWeatherView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = CommonCurrentWeatherViewBinding.inflate(LayoutInflater.from(context))

    var temperature: String = "???"
        set(value) {
            field = value
            binding.temperatureView.text = value
        }

    var weatherIconUrl: String? = null
        set(value) {
            field = value
            // TODO
        }

    var description: String? = null
        set(value) {
            field = value
            val text = value ?: "???"
            binding.descriptionView.text = text
        }

    var temperatureFeelsLike: String = "???"
        set(value) {
            field = value
            binding.feelsLikeView.text = value
        }

    var windSpeed: String = "???"
        set(value) {
            field = value
            binding.windView.text = value
        }

    var humidity: String = "???"
        set(value) {
            field = value
            binding.humidityView.text = value
        }

    init {
        addView(binding.root)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        binding.container.setOnClickListener(l)
    }
}