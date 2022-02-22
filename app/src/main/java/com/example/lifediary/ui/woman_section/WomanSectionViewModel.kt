package com.example.lifediary.ui.woman_section

import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class WomanSectionViewModel : BaseViewModel() {
    @Inject lateinit var router: Router

    init {
        bindScope()
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.WOMAN_SECTION_SCOPE)
        Toothpick.inject(this, womanSectionScope)
    }
}