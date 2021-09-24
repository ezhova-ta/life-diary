package com.example.lifediary.ui.activity

import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.toDomain
import com.github.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

class MainActivityViewModel : BaseViewModel() {
    @Inject lateinit var router: Router

    init {
        bindAppScope()
        routeToMainScreen()
    }

    private fun routeToMainScreen() {
        router.newRootChain(Screens.getMainScreen())
    }

    fun onShowCurrentCalendarDayActionRequested() {
        val today = Calendar.getInstance().toDomain()
        router.newChain(Screens.getCalendarScreen(), Screens.getCalendarDateScreen(today))
    }
}