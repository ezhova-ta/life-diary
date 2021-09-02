package com.example.lifediary.ui.main.notes

import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainNotesViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router

	init {
		bindAppScope()
	}
}