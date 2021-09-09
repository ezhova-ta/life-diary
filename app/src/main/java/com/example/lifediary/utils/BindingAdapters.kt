package com.example.lifediary.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.lifediary.ui.common.CurrentWeatherView

@BindingAdapter("booleanVisibility")
fun View.setBooleanVisibility(isVisible: Boolean) {
	this.isVisible = isVisible
}

@BindingAdapter("progressVisibility")
fun CurrentWeatherView.setProgressVisibility(visibility: Boolean) {
	progressIsVisible = visibility
}