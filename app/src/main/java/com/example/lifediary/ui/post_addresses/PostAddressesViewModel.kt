package com.example.lifediary.ui.post_addresses

import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class PostAddressesViewModel: BaseViewModel() {
    @Inject
    lateinit var router: Router

    init {
        bindAppScope()
    }

    fun onAddPostAddressClick() {
        router.navigateTo(Screens.getAddEditPostAddressScreen())
    }
}