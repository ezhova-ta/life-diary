package com.example.lifediary.presentation.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.lifediary.presentation.ui.common.CurrentWeatherView
import com.example.lifediary.presentation.ui.common.WeatherForecastView

@BindingAdapter("booleanVisibility")
fun View.setBooleanVisibility(isVisible: Boolean) {
	this.isVisible = isVisible
}

@BindingAdapter("progressVisibility")
fun CurrentWeatherView.setProgressVisibility(visibility: Boolean) {
	progressIsVisible = visibility
}

@BindingAdapter("progressVisibility")
fun WeatherForecastView.setProgressVisibility(visibility: Boolean) {
	progressIsVisible = visibility
}