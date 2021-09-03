package com.example.lifediary.ui.calendar.date

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.data.domain.WeatherForecast
import com.example.lifediary.data.repositories.DateNoteRepository
import com.example.lifediary.data.repositories.ToDoListRepository
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.isSameDay
import com.example.lifediary.utils.toDateString
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarDateViewModel(private val day: Day) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var weatherRepository: WeatherRepository
	@Inject lateinit var noteRepository: DateNoteRepository
	@Inject lateinit var toDoListRepository: ToDoListRepository
	val title = day.toDateString()
	val noteText: LiveData<String?>
	val isNoteVisible: LiveData<Boolean>
	private val note: LiveData<DateNote?>

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { day.isSameDay(it.dateInSeconds) }
	}

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }
	val weatherForecastIconUrl = weatherForecastForDate.map { it?.weather?.firstOrNull()?.iconUrl }
	val weatherForecastDescription = weatherForecastForDate.map { it?.weather?.firstOrNull()?.description }

	init {
		bindAppScope()
		note = noteRepository.getNoteLiveData(day)
		noteText = note.map { it?.text }
		isNoteVisible = note.map { it != null }

		viewModelScope.launch(Dispatchers.IO) {
			try {
				val locationId = weatherRepository.getLocation()?.id ?: throw NullPointerException()
				val forecast = weatherRepository.getForecast(locationId)
				weatherForecast.postValue(forecast)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_get_forecast))
			}
		}
	}

	fun onAddNoteClick() {
		navigateToAddEditNoteScreen()
	}

	fun onEditNoteClick() {
		navigateToAddEditNoteScreen()
	}

	fun onDeleteNoteClick() {
		val noteId = note.value?.id ?: return

		CoroutineScope(Dispatchers.IO).launch {
			try {
				noteRepository.deleteNote(noteId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error))
			}
		}
	}

	private fun navigateToAddEditNoteScreen() {
		router.navigateTo(Screens.getAddEditDateNoteFragment(day))
	}

	class Factory(private val day: Day) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return CalendarDateViewModel(day) as T
		}
	}
}