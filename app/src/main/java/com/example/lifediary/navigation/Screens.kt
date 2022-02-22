package com.example.lifediary.navigation

import com.example.lifediary.ui.calendar.CalendarFragment
import com.example.lifediary.ui.calendar.date.CalendarDateFragment
import com.example.lifediary.ui.calendar.date.note.AddEditDateNoteFragment
import com.example.lifediary.ui.location.selection.LocationSelectionFragment
import com.example.lifediary.ui.main.MainFragment
import com.example.lifediary.ui.main.notes.AddEditMainNoteFragment
import com.example.lifediary.ui.main.notes.MainNotesFragment
import com.example.lifediary.ui.memorable_dates.AddEditMemorableDateFragment
import com.example.lifediary.ui.memorable_dates.MemorableDatesFragment
import com.example.lifediary.ui.post_addresses.AddEditPostAddressFragment
import com.example.lifediary.ui.post_addresses.PostAddressesFragment
import com.example.lifediary.ui.settings.SettingsFragment
import com.example.lifediary.ui.shopping_list.ShoppingListFragment
import com.example.lifediary.ui.woman_section.WomanSectionFragment
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
    fun getAddEditDateNoteScreen(day: Day) =
        FragmentScreen { AddEditDateNoteFragment.getInstance(day) }
    fun getPostAddressesScreen() = FragmentScreen { PostAddressesFragment.getInstance() }
    fun getAddEditPostAddressScreen() = FragmentScreen { AddEditPostAddressFragment.getInstance() }
    fun getMainNotesScreen() = FragmentScreen { MainNotesFragment.getInstance() }
    fun getAddEditMainNoteScreen(noteId: Long) =
        FragmentScreen { AddEditMainNoteFragment.getInstance(noteId) }
    fun getAddEditMainNoteScreen() =
        FragmentScreen { AddEditMainNoteFragment.getInstance(null) }
    fun getMemorableDatesScreen() = FragmentScreen { MemorableDatesFragment.getInstance() }
    fun getAddEditMemorableDateScreen(dateId: Long) =
        FragmentScreen { AddEditMemorableDateFragment.getInstance(dateId) }
    fun getAddEditMemorableDateScreen() =
        FragmentScreen { AddEditMemorableDateFragment.getInstance(null) }
    fun getWomanSectionScreen() =
        FragmentScreen { WomanSectionFragment.getInstance() }
}