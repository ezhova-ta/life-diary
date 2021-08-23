package com.example.lifediary.ui.calendar.date

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.data.domain.WeatherForecast
import com.example.lifediary.data.repositories.NotesRepository
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.*
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarDateViewModel(private val day: Day) : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var weatherRepository: WeatherRepository
	@Inject
	lateinit var notesRepository: NotesRepository

	val title = day.toDateString()
	private val notes: LiveData<Notes?>
	val notesText: LiveData<String?>
	val isNotesVisible: LiveData<Boolean>

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { day.isSameDay(it.dateInSeconds) }
	}

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }
	val weatherForecastIconUrl = weatherForecastForDate.map { it?.weather?.firstOrNull()?.iconUrl }
	val weatherForecastDescription = weatherForecastForDate.map { it?.weather?.firstOrNull()?.description }

	init {
		bindAppScope()
		notes = notesRepository.getNotesLiveData(day)
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
		navigateToAddEditNotesScreen()
	}

	fun onEditNotesClick() {
		navigateToAddEditNotesScreen()
	}

	fun onDeleteNotesClick() {
		val notesId = notes.value?.id ?: return

		CoroutineScope(Dispatchers.IO).launch {
			try {
				notesRepository.deleteNotes(notesId)
			} catch(e: Exception) {
				val messageRes = R.string.error
				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
			}
		}
	}

	private fun navigateToAddEditNotesScreen() {
		router.navigateTo(Screens.getAddEditNotesFragment(day))
	}

	class Factory(private val day: Day) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return CalendarDateViewModel(day) as T
		}
	}
}