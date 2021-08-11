package com.example.lifediary.utils

open class OneTimeEvent {
	var hasBeenHandled = false
	private set

	fun shouldBeHandled(): Boolean {
		return if(hasBeenHandled) {
			false
		} else {
			hasBeenHandled = true
			true
		}
	}
}