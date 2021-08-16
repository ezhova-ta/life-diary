package com.example.lifediary.utils

fun Int.createStringWithPlusOrMinusSign(): String {
	if(this < 0) return toString()
	return String.format("+%d", this)
}