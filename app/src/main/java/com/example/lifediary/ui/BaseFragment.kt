package com.example.lifediary.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lifediary.utils.InsetsStyle
import com.example.lifediary.utils.setInsetsStyle

open class BaseFragment : Fragment() {
    // TODO Nullable return type?
    protected open fun getInsetsStyle(): InsetsStyle? =
        InsetsStyle.defaultStyle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInsetsStyle()
    }

    private fun setInsetsStyle() {
        val insetsStyle = getInsetsStyle() ?: return
        val activity = activity ?: return

        val isInsetsLight = insetsStyle is InsetsStyle.Light
        activity.setInsetsStyle(insetsStyle.insetsColor, isInsetsLight)
    }

    protected fun showLongPopupMessage(resId: Int) {
        showPopupMessage(resources.getText(resId), true)
    }

    protected fun showShortPopupMessage(resId: Int) {
        showPopupMessage(resources.getText(resId), false)
    }

    private fun showPopupMessage(text: CharSequence, isLong: Boolean) {
        val duration = if(isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(requireActivity(), text, duration).show()
    }
}