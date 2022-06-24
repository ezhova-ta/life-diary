package com.example.lifediary.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.lifediary.R

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

fun EditText.clearFocusWithKeyboard(activity: Activity?) {
    clearFocus()
    activity?.hideKeyboard()
}

private fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun AlertDialog.setDefaultButtonsStyle() {
    getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(
        context.resources.getColor(R.color.app_blue, context.theme)
    )

    getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
        context.resources.getColor(R.color.black_opacity_50, context.theme)
    )
}