package com.example.lifediary.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.lifediary.ui.common.CommonCurrentWeatherView

// TODO Correct fun name
fun EditText.requestFocusWithKeyboard() {
    requestFocus()
    showKeyboard()
}

private fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.findFocus()?.let {
        imm.showSoftInput(it, InputMethodManager.SHOW_FORCED)
    }
}

// TODO Correct fun name
fun EditText.clearFocusWithKeyboard(activity: Activity?) {
    clearFocus()
    activity?.hideKeyboard()
}

private fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

@BindingAdapter("progressVisibility")
fun CommonCurrentWeatherView.setProgressVisibility(visibility: Boolean) {
    progressIsVisible = visibility
}