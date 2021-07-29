package com.example.lifediary

import android.app.Application
import com.example.lifediary.di.AppModule
import com.example.lifediary.di.DiScopes
import com.github.terrakok.cicerone.Cicerone
import toothpick.Toothpick

class LifeDiaryApplication : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigationHolder get() = cicerone.getNavigatorHolder()

    companion object {
        internal lateinit var INSTANCE: LifeDiaryApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        injectAppScope()
    }

    private fun injectAppScope() {
        Toothpick.openScope(DiScopes.APP_SCOPE).installModules(AppModule(INSTANCE))
    }
}