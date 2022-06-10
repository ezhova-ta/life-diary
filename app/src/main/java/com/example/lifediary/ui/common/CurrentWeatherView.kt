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
                Glide.with(context).load(value).into(binding.weatherIcon)
            }
        }

    var description: String? = null
        set(value) {
            field = value

            with(binding.descriptionView) {
                if(value == null) {
                    isVisible = false
                } else {
                    text = value
                    isVisible = true
                }
            }
        }

    var temperatureFeelsLike: String? = null
        set(value) {
            field = value

            with(binding.feelsLikeView) {
                if (value == null) {
                    isVisible = false
                } else {
                    isVisible = true
                    text = value
                }
            }
        }

    var windSpeed: String? = null
        set(value) {
            field = value

            with(binding.windView) {
                if (value == null) {
                    isVisible = false
                } else {
                    isVisible = true
                    text = value
                }
            }
        }

    var humidity: String? = null
        set(value) {
            field = value

            with(binding.humidityView) {
                if (value == null) {
                    isVisible = false
                } else {
                    isVisible = true
                    text = value
                }
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