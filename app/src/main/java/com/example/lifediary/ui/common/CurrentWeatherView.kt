package com.example.lifediary.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.lifediary.databinding.CurrentWeatherViewBinding

class CurrentWeatherView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = CurrentWeatherViewBinding.inflate(LayoutInflater.from(context))

    var progressIsVisible: Boolean
        get() = binding.progressView.isVisible
        set(value) {
            binding.progressView.isVisible = value
        }

    var temperature: String? = null
        set(value) {
            field = value
            binding.temperatureView.text = value
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

    var description: String? = null
        set(value) {
            field = value

            if(value == null) {
                binding.descriptionView.isVisible = false
            } else {
                binding.descriptionView.text = value
                binding.descriptionView.isVisible = true
            }
        }

    var temperatureFeelsLike: String? = null
        set(value) {
            field = value

            if(value == null) {
                binding.feelsLikeView.isVisible = false
            } else {
                binding.feelsLikeView.isVisible = true
                binding.feelsLikeView.text = value
            }
        }

    var windSpeed: String? = null
        set(value) {
            field = value

            if(value == null) {
                binding.windContainer.isVisible = false
            } else {
                binding.windContainer.isVisible = true
                binding.windView.text = value
            }
        }

    var humidity: String? = null
        set(value) {
            field = value

            if(value == null) {
                binding.humidityContainer.isVisible = false
            } else {
                binding.humidityContainer.isVisible = true
                binding.humidityView.text = value
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