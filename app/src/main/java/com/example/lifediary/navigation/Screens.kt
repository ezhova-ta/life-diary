package com.example.lifediary.navigation

import com.example.lifediary.ui.calendar.CalendarFragment
import com.example.lifediary.ui.calendar.date.CalendarDateFragment
import com.example.lifediary.ui.location.selection.LocationSelectionFragment
import com.example.lifediary.ui.main.MainFragment
import com.example.lifediary.ui.notes.AddNotesFragment
import com.example.lifediary.ui.settings.SettingsFragment
import com.example.lifediary.ui.shopping_list.ShoppingListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun getMainScreen() = FragmentScreen { MainFragment.getInstance() }
    fun getCalendarScreen() = FragmentScreen { CalendarFragment.getInstance() }
    fun getCalendarDateScreen(timeInMillis: Long) =
        FragmentScreen { CalendarDateFragment.getInstance(timeInMillis) }
    fun getShoppingListScreen() = FragmentScreen { ShoppingListFragment.getInstance() }
    fun getSettingsScreen() = FragmentScreen { SettingsFragment.getInstance() }
    fun getLocationSelectionScreen() = FragmentScreen { LocationSelectionFragment.getInstance() }
    fun getAddNotesFragment() = FragmentScreen { AddNotesFragment.getInstance() }
}