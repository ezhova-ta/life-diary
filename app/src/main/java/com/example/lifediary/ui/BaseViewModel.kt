package com.example.lifediary.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifediary.di.DiScopes
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import toothpick.Toothpick

open class BaseViewModel : ViewModel() {
    val popupMessageEvent = MutableLiveData<OneTimeEvent<Text>>()

    protected fun bindAppScope() {
        val appScope = Toothpick.openScope(DiScopes.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }
}