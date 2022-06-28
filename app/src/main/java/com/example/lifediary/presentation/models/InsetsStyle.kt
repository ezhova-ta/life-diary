package com.example.lifediary.presentation.models

import android.graphics.Color
import androidx.annotation.ColorInt

sealed class InsetsStyle(@ColorInt val insetsColor: Int) {
    class Light(@ColorInt insetsColor: Int) : InsetsStyle(insetsColor)
    class Dark(@ColorInt insetsColor: Int) : InsetsStyle(insetsColor)

    companion object {
        val defaultStyle = Light(Color.WHITE)
    }
}