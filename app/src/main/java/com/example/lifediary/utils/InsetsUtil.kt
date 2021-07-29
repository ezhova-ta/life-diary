package com.example.lifediary.utils

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat

object InsetsUtil {
    fun Activity.setInsetsStyle(@ColorInt color: Int, isLight: Boolean) {
        setInsetsColor(color)
        setInsetsContentTint(isLight)
    }

    private fun Activity.setInsetsColor(@ColorInt color: Int) {
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView, null)
        window.statusBarColor = color
        window.navigationBarColor = color
    }

    private fun Activity.setInsetsContentTint(isLight: Boolean) {
        window.decorView.systemUiVisibility = if(isLight) {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            0
        }
    }
}