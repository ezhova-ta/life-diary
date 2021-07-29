package com.example.lifediary.navigation

import com.example.lifediary.ui.calendar.CalendarFragment
import com.example.lifediary.ui.main.MainFragment
import com.example.lifediary.ui.shopping_list.ShoppingListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun getMainScreen() = FragmentScreen { MainFragment() }
    fun getCalendarScreen() = FragmentScreen { CalendarFragment() }
    fun getShoppingListScreen() = FragmentScreen { ShoppingListFragment() }
}