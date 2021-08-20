package com.example.lifediary.ui.calendar.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.lifediary.R
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.data.domain.WeatherForecast
import com.example.lifediary.data.repositories.NotesRepository
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.isSameDay
import com.example.lifediary.utils.toDateString
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CalendarDateViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var weatherRepository: WeatherRepository
	@Inject
	lateinit var notesRepository: NotesRepository

	lateinit var date: Calendar
	val title: String by lazy { date.toDateString() }
	private val notes: LiveData<Notes?>
	val notesText: LiveData<String?>
	val isNotesVisible: LiveData<Boolean>

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { date.isSameDay(it.dateInSeconds) }
	}

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }
	val weatherForecastIconUrl = weatherForecastForDate.map { it?.weather?.firstOrNull()?.iconUrl }
	val weatherForecastDescription = weatherForecastForDate.map { it?.weather?.firstOrNull()?.description }

	init {
		bindAppScope()
		notes = notesRepository.getNotesLiveData()
		notesText = notes.map { it?.text }
		isNotesVisible = notes.map { it != null }

		viewModelScope.launch(Dispatchers.IO) {
			try {
				val locationId = weatherRepository.getLocation()?.id ?: throw NullPointerException()
				val forecast = weatherRepository.getForecast(locationId)
				weatherForecast.postValue(forecast)
			} catch(e: Exception) {
				val messageRes = R.string.failed_to_get_forecast
				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
			}
		}
	}

	fun onAddNotesClick() {
		// TODO Real text
		val testNotesText = "This book is a treatise on the theory of ethics, very popular during the Renaissance."

		CoroutineScope(Dispatchers.IO).launch {
			notesRepository.saveNotes(testNotesText)
		}
	}

	fun onEditNotesClick() {
		TODO()
	}
}