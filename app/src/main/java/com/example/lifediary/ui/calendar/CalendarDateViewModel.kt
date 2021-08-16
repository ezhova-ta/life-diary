package com.example.lifediary.ui.calendar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.lifediary.R
import com.example.lifediary.data.domain.WeatherForecast
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.isSameDay
import com.example.lifediary.utils.toDateString
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CalendarDateViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var repository: WeatherRepository

	lateinit var date: Calendar
	val title: String by lazy { date.toDateString() }

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	private val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { date.isSameDay(it.dateInSeconds) }
	}

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }

	val dayTemperatureForDate = weatherForecastForDate.map { it?.temperature?.dayString }
	val nightTemperatureForDate = weatherForecastForDate.map { it?.temperature?.nightString }

	init {
		bindAppScope()

		viewModelScope.launch(Dispatchers.IO) {
			try {
				val locationId = repository.getLocation()?.id ?: throw NullPointerException()
				val forecast = repository.getForecast(locationId)
				weatherForecast.postValue(forecast)
			} catch(e: Exception) {
				val messageRes = R.string.failed_to_get_forecast
				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
			}
		}
	}
}