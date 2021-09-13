package com.example.lifediary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifediary.di.DiScopes
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import toothpick.Toothpick

open class BaseViewModel : ViewModel() {
    private val _popupMessageEvent = MutableLiveData<OneTimeEvent<Text>>()
    val popupMessageEvent: LiveData<OneTimeEvent<Text>>
        get() = _popupMessageEvent

    protected fun bindAppScope() {
        val appScope = Toothpick.openScope(DiScopes.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }

    protected fun bindSettingsScope() {
        val settingsScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.SETTINGS_SCOPE)
        Toothpick.inject(this, settingsScope)
    }

    protected fun showMessage(text: Text) {
        _popupMessageEvent.postValue(OneTimeEvent(text))
    }
}