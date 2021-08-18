package com.example.lifediary.ui.common

import android.view.View
import com.example.lifediary.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer

class CalendarDayViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)
    val textView = binding.dayText
    lateinit var day: CalendarDay

    fun setOnClickListener(onClick: (CalendarDay) -> Unit) {
        view.setOnClickListener {
            onClick(day)
        }
    }

    fun setSelectedStyle() {
        binding.selectedBackground.visibility = View.VISIBLE
    }

    fun setNormalStyle() {
        binding.selectedBackground.visibility = View.INVISIBLE
    }

    fun showNoteIcon() {
        binding.noteIcon.visibility = View.VISIBLE
    }

    fun hideNoteIcon() {
        binding.noteIcon.visibility = View.INVISIBLE
    }
}