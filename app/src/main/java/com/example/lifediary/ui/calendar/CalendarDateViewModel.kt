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
	val noteText = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source."
	val noteRolledUpMaxLines = 5
	val isAddNoteVisible = true
	val isNoteVisible = false

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { date.isSameDay(it.dateInSeconds) }
	}

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }
	val weatherForecastIconUrl = weatherForecastForDate.map { it?.weather?.firstOrNull()?.iconUrl }
	val weatherForecastDescription = weatherForecastForDate.map { it?.weather?.firstOrNull()?.description }

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

	fun onAddNoteClick() {
		Log.d("fffffffffffff", "onAddNoteClick")
	}

	fun onEditNoteClick() {
		Log.d("fffffffffffff", "onEditNoteClick")
	}
}