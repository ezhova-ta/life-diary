package com.example.lifediary.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lifediary.R
import com.example.lifediary.databinding.DateViewBinding
import com.example.lifediary.utils.isDayOff
import java.util.*

class DateView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding = DateViewBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
        setupDayOfMonthView()
    }

    private fun setupDayOfMonthView() {
        binding.dayOfMonthView.setTextColor(
            if(Calendar.getInstance().isDayOff()) {
                context.resources.getColor(R.color.app_red, context.theme)
            } else {
                context.resources.getColor(R.color.app_green, context.theme)
            }
        )
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        binding.container.setOnClickListener(l)
    }
}