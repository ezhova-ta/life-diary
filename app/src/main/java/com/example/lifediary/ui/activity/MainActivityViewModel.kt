package com.example.lifediary.ui.activity

import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
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
}