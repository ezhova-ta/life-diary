package com.example.lifediary.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lifediary.utils.InsetsStyle
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.setInsetsStyle

abstract class BaseFragment : Fragment() {
    protected abstract val viewModel: BaseViewModel

    // TODO Nullable return type?
    protected open fun getInsetsStyle(): InsetsStyle? =
        InsetsStyle.defaultStyle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInsetsStyle()
        setupMessageShowing()
    }

    private fun setInsetsStyle() {
        val insetsStyle = getInsetsStyle() ?: return
        val activity = activity ?: return

        val isInsetsLight = insetsStyle is InsetsStyle.Light
        activity.setInsetsStyle(insetsStyle.insetsColor, isInsetsLight)
    }

    private fun setupMessageShowing() {
        viewModel.popupMessageEvent.observe(viewLifecycleOwner) { event ->
            event.getData()?.let { message ->
                showPopupMessage(message)
            }
        }
    }

    private fun showPopupMessage(text: Text) {
        Toast.makeText(
            requireActivity(),
            text.getText(requireContext()),
            Toast.LENGTH_LONG
        ).show()
    }
}