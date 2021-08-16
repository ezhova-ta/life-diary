package com.example.lifediary.data.domain

import com.example.lifediary.utils.createStringWithPlusOrMinusSign

data class WeatherForecastTemperature(
    val day: Int,
    val night: Int,
    val morning: Int,
    val min: Int? = null,
    val max: Int? = null
) {
	val dayString = day.createStringWithPlusOrMinusSign()
	val nightString = night.createStringWithPlusOrMinusSign()
	val morningString = morning.createStringWithPlusOrMinusSign()
	val minString = min?.createStringWithPlusOrMinusSign()
	val maxString = max?.createStringWithPlusOrMinusSign()
}
