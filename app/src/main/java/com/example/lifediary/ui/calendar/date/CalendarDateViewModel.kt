package com.example.lifediary.ui.calendar.date

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.Day
import com.example.lifediary.data.domain.Text
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.data.domain.WeatherForecast
import com.example.lifediary.data.repositories.*
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.*
import com.example.lifediary.utils.livedata.TwoSourceLiveData
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class CalendarDateViewModel(private val day: Day) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var weatherRepository: WeatherRepository
	@Inject lateinit var noteRepository: DateNoteRepository
	@Inject lateinit var toDoListRepository: ToDoListRepository
	@Inject lateinit var memorableDatesRepository: MemorableDatesRepository
	@Inject lateinit var womanSectionRepository: WomanSectionRepository
	@Inject lateinit var settingsRepository: SettingsRepository
	val title = day.toDateString()
	val toDoList by lazy { toDoListRepository.getToDoList(day) }
	val isToDoListVisible by lazy { toDoList.map { it.isNotEmpty() } }
	private val note by lazy { noteRepository.getNoteLiveData(day) }
	val noteText by lazy { note.map { it?.text } }
	val isNoteVisible by lazy { note.map { it != null } }
	val newToDoListItemText = MutableLiveData("")
	val memorableDates by lazy { memorableDatesRepository.getDates(day) }
	val isMemorableDatesVisible by lazy { memorableDates.map { it.isNotEmpty() } }
	val isMenstruationIconVisible by lazy { getMenstruationIconVisibility() }
	val isEstimatedMenstruationIconVisible by lazy { getEstimatedMenstruationIconVisibility() }
	val isCalendarIconVisible by lazy { getCalendarIconVisibility() }

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { day.isSameDay(it.dateInSeconds) }
	}

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }
	val weatherForecastIconUrl = weatherForecastForDate.map { it?.weather?.firstOrNull()?.iconUrl }
	val weatherForecastDescription = weatherForecastForDate.map { it?.weather?.firstOrNull()?.description }

	private val _showClearToDoListConfirmationDialog = MutableLiveData(false)
	val showClearToDoListConfirmationDialog: LiveData<Boolean>
		get() = _showClearToDoListConfirmationDialog

	private val _showDeleteNoteConfirmationDialog = MutableLiveData(false)
	val showDeleteNoteConfirmationDialog: LiveData<Boolean>
		get() = _showDeleteNoteConfirmationDialog

	private val _toDoListItemScheduleNotificationEvent = MutableLiveData<OneTimeEvent<ToDoListItem>>()
	val toDoListItemScheduleNotificationEvent: LiveData<OneTimeEvent<ToDoListItem>>
		get() = _toDoListItemScheduleNotificationEvent

	private val _toDoListItemCancelScheduledNotificationEvent = MutableLiveData<OneTimeEvent<ToDoListItem>>()
	val toDoListItemCancelScheduledNotificationEvent: LiveData<OneTimeEvent<ToDoListItem>>
		get() = _toDoListItemCancelScheduledNotificationEvent

	init {
		bindScope()
		loadForecast()
	}

	override fun bindScope() {
		val calendarDateScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.CALENDAR_DATE_SCOPE)
		Toothpick.inject(this, calendarDateScope)
	}

	private fun loadForecast() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val locationId = weatherRepository.getLocation()?.id ?: throw NullPointerException()
				val forecast = weatherRepository.getForecast(locationId)
				weatherForecast.postValue(forecast)
			} catch(e: Exception) {
				// TODO Message display temporarily removed
//				showMessage(Text.TextResource(R.string.failed_to_get_forecast))
			}
		}
	}

	private fun getMenstruationIconVisibility(): LiveData<Boolean> {
		return TwoSourceLiveData<Boolean, Boolean, Boolean>(
			settingsRepository.getWomanSectionEnabled(),
			womanSectionRepository.isDayOfMenstruationPeriod(day)
		) { isWomanSectionEnabled, isDayOfMenstruationPeriod ->
			isWomanSectionEnabled == true && isDayOfMenstruationPeriod == true
		}
	}

	private fun getEstimatedMenstruationIconVisibility(): LiveData<Boolean> {
		return TwoSourceLiveData<Boolean, Boolean, Boolean>(
			settingsRepository.getWomanSectionEnabled(),
			womanSectionRepository.isDayOfEstimatedMenstruationPeriod(day)
		) { isWomanSectionEnabled, isDayOfEstimatedMenstruationPeriod ->
			isWomanSectionEnabled == true && isDayOfEstimatedMenstruationPeriod == true
		}
	}

	private fun getCalendarIconVisibility(): LiveData<Boolean> {
		return TwoSourceLiveData<Boolean, Boolean, Boolean>(
			settingsRepository.getWomanSectionEnabled(),
			womanSectionRepository.isDayNotIncludedInMenstruationPeriod(day)
		) { isWomanSectionEnabled, dayNotIncludedInMenstruationPeriod ->
			if(isWomanSectionEnabled == false) return@TwoSourceLiveData true
			dayNotIncludedInMenstruationPeriod == true
		}
	}

	fun onNoteLongClick() {
		val noteText = note.value?.text ?: return
		copyToClipboard(noteText)
		showMessage(Text.TextResource(R.string.text_copied))
	}

	fun onAddNoteClick() {
		navigateToAddEditNoteScreen()
	}

	fun onEditNoteClick() {
		navigateToAddEditNoteScreen()
	}

	fun onDeleteNoteClick() {
		note.value?.id ?: return
		_showDeleteNoteConfirmationDialog.value = true
	}

	fun onDeleteNoteConfirmed() {
		_showDeleteNoteConfirmationDialog.value = false
		val noteId = note.value?.id ?: return
		deleteNote(noteId)
	}

	private fun deleteNote(noteId: Long) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				noteRepository.deleteNote(noteId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error))
			}
		}
	}

	fun onDeleteNoteCancelled() {
		_showDeleteNoteConfirmationDialog.value = false
	}

	private fun navigateToAddEditNoteScreen() {
		router.navigateTo(Screens.getAddEditDateNoteScreen(day))
	}

	fun onClearToDoListClick() {
		if(toDoList.value.isNullOrEmpty()) return
		_showClearToDoListConfirmationDialog.value = true
	}

	fun onClearToDoListConfirmed() {
		_showClearToDoListConfirmationDialog.value = false
		cancelToDoListNotifications()
		clearToDoList()
	}

	private fun cancelToDoListNotifications() {
		toDoList.value?.forEach {
			if(it.notificationEnabled) {
				_toDoListItemCancelScheduledNotificationEvent.value = OneTimeEvent(it)
			}
		}
	}

	private fun clearToDoList() {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				toDoListRepository.clearToDoList(day)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_clear_list))
			}
		}
	}

	fun onClearToDoListCancelled() {
		_showClearToDoListConfirmationDialog.value = false
	}

	fun onAddToDoListItemClick() {
		onAddToDoListItemInputDone()
	}

	fun onAddToDoListItemInputDone() {
		val text = newToDoListItemText.value?.trim()

		if(text.isNullOrBlank()) {
			newToDoListItemText.value = ""
			return
		}

		val item = ToDoListItem(text = text, day = day)

		CoroutineScope(Dispatchers.IO).launch {
			try {
				toDoListRepository.saveToDoListItem(item)
				newToDoListItemText.postValue("")
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_save))
			}
		}
	}

	fun onDeleteToDoListItemClick(item: ToDoListItem) {
		val itemId = item.id ?: return
		if(item.notificationEnabled)
			_toDoListItemCancelScheduledNotificationEvent.value = OneTimeEvent(item)
		deleteToDoListItem(itemId)
	}

	private fun deleteToDoListItem(itemId: Long) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				toDoListRepository.deleteToDoListItem(itemId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.deleting_item_error))
			}
		}
	}

	fun onToDoListItemNotificationClick(item: ToDoListItem) {
		if(item.notificationEnabled) {
			cancelScheduledToDoListItemNotification(item)
		} else {
			scheduleToDoListItemNotification(item)
		}
	}

	private fun cancelScheduledToDoListItemNotification(item: ToDoListItem) {
		val itemId = item.id ?: return
		_toDoListItemCancelScheduledNotificationEvent.value = OneTimeEvent(item)

		CoroutineScope(Dispatchers.IO).launch {
			try {
				toDoListRepository.disableListItemNotification(itemId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error_try_again_later))
			}
		}
	}

	private fun scheduleToDoListItemNotification(toDoListItem: ToDoListItem) {
		_toDoListItemScheduleNotificationEvent.value = OneTimeEvent(toDoListItem)
	}

	fun onToDoListItemNotificationScheduled(item: ToDoListItem, time: Calendar) {
		val itemId = item.id ?: return

		CoroutineScope(Dispatchers.IO).launch {
			try {
				toDoListRepository.enableListItemNotification(itemId, time)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error_try_again_later))
			}
		}
	}

	fun onSchedulingToDoListItemNotificationCancelled(item: ToDoListItem) {}

	fun onToDoListItemClick(item: ToDoListItem) {
		val itemId = item.id ?: return

		CoroutineScope(Dispatchers.IO).launch {
			try {
				toDoListRepository.inverseListItemIsDone(itemId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error_try_again_later))
			}
		}
	}

	fun onToDoListItemLongClick(item: ToDoListItem) {
		copyToClipboard(item.text)
		showMessage(Text.TextResource(R.string.text_copied))
	}

	class Factory(private val day: Day) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return CalendarDateViewModel(day) as T
		}
	}

	override fun onCleared() {
		Toothpick.closeScope(DiScopes.CALENDAR_DATE_SCOPE)
		super.onCleared()
	}
}