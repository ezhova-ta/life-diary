package com.example.lifediary.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.dates.CalendarBuilder
import com.example.lifediary.utils.dates.DatesConverter.calendarToDay
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class MainActivityViewModel : BaseViewModel() {
    @Inject lateinit var router: Router

    private val _isNetworkConnectivityAvailable = MutableLiveData(false)
    val isNetworkConnectivityAvailable: LiveData<Boolean>
        get() = _isNetworkConnectivityAvailable

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
        val today = calendarToDay(CalendarBuilder().build())
        router.newChain(Screens.getCalendarScreen(), Screens.getCalendarDateScreen(today))
    }

    fun onNetworkConnectivityAvailable() {
        _isNetworkConnectivityAvailable.value = true
    }

    fun onNetworkConnectivityLost() {
        _isNetworkConnectivityAvailable.value = false
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.APP_SCOPE)
        super.onCleared()
    }
}