package com.example.lifediary.ui.main

import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    @Inject
    lateinit var router: Router

    init {
        bindAppScope()
    }

    fun onCalendarClick() {
        router.navigateTo(Screens.getCalendarScreen())
    }

    fun onShoppingListClick() {
        router.navigateTo(Screens.getShoppingListScreen())
    }
}