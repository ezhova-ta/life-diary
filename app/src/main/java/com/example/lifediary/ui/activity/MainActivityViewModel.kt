package com.example.lifediary.ui.activity

import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.toDomain
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class MainActivityViewModel : BaseViewModel() {
    @Inject lateinit var router: Router

    init {
        bindScope()
        routeToMainScreen()
    }

    override fun bindScope() {
        val appScope = Toothpick.openScope(DiScopes.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }

    private fun routeToMainScreen() {
        router.newRootChain(Screens.getMainScreen())
    }

    fun onShowCurrentCalendarDayActionRequested() {
        val today = Calendar.getInstance().toDomain()
        router.newChain(Screens.getCalendarScreen(), Screens.getCalendarDateScreen(today))
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.APP_SCOPE)
        super.onCleared()
    }
}