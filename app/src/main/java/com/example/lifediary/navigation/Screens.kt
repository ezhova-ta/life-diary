package com.example.lifediary.navigation

import com.example.lifediary.ui.calendar.CalendarFragment
import com.example.lifediary.ui.calendar.date.CalendarDateFragment
import com.example.lifediary.ui.location.selection.LocationSelectionFragment
import com.example.lifediary.ui.main.MainFragment
import com.example.lifediary.ui.note.AddEditNoteFragment
import com.example.lifediary.ui.post_addresses.AddEditPostAddressFragment
import com.example.lifediary.ui.post_addresses.PostAddressesFragment
import com.example.lifediary.ui.settings.SettingsFragment
import com.example.lifediary.ui.shopping_list.ShoppingListFragment
import com.example.lifediary.utils.Day
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun getMainScreen() = FragmentScreen { MainFragment.getInstance() }
    fun getCalendarScreen() = FragmentScreen { CalendarFragment.getInstance() }
    fun getCalendarDateScreen(day: Day) =
        FragmentScreen { CalendarDateFragment.getInstance(day) }
    fun getShoppingListScreen() = FragmentScreen { ShoppingListFragment.getInstance() }
    fun getSettingsScreen() = FragmentScreen { SettingsFragment.getInstance() }
    fun getLocationSelectionScreen() = FragmentScreen { LocationSelectionFragment.getInstance() }
    fun getAddEditNoteFragment(day: Day) =
        FragmentScreen { AddEditNoteFragment.getInstance(day) }
    fun getPostAddressesScreen() = FragmentScreen { PostAddressesFragment.getInstance() }
    fun getAddEditPostAddressScreen() = FragmentScreen { AddEditPostAddressFragment.getInstance() }
}