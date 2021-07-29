package com.example.lifediary.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lifediary.utils.InsetsStyle
import com.example.lifediary.utils.InsetsUtil.setInsetsStyle

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
}