package com.example.lifediary.ui

import androidx.lifecycle.ViewModel
import com.example.lifediary.di.DiScopes
import toothpick.Toothpick

open class BaseViewModel : ViewModel() {
    protected fun bindAppScope() {
        val appScope = Toothpick.openScope(DiScopes.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }
}