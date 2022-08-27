package com.example.lifediary.presentation.ui.calendar.date

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.CALENDAR_DATE_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.models.WeatherForecast
import com.example.lifediary.domain.usecases.calendar.*
import com.example.lifediary.domain.usecases.location.GetLocationUseCase
import com.example.lifediary.domain.usecases.settings.GetWomanSectionEnabledUseCase
import com.example.lifediary.domain.usecases.weather.GetForecastForLocationIdUseCase
import com.example.lifediary.domain.usecases.woman_section.GetAllMenstruationPeriodsUseCase
import com.example.lifediary.domain.usecases.woman_section.GetEstimatedNextMenstruationPeriodUseCase
import com.example.lifediary.domain.utils.sorters.ToDoListSortMethod
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.models.dropdowns.ToDoListDropDownItemSortMethodMapper.toSortMethod
import com.example.lifediary.presentation.models.dropdowns.ToDoListSortMethodDropDownItem
import com.example.lifediary.presentation.navigation.Screens
import com.example.lifediary.presentation.ui.BaseViewModel
import com.example.lifediary.presentation.utils.OneTimeEvent
import com.example.lifediary.presentation.utils.dates.isSameDay
import com.example.lifediary.presentation.utils.dates.isWithinInterval
import com.example.lifediary.presentation.utils.dates.toDateString
import com.example.lifediary.presentation.utils.dayString
import com.example.lifediary.presentation.utils.nightString
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class CalendarDateViewModel(private val day: Day) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var getLocationUseCase: GetLocationUseCase
	@Inject lateinit var getForecastForLocationIdUseCase: GetForecastForLocationIdUseCase
	@Inject lateinit var getDateNoteLiveDataUseCase: GetDateNoteLiveDataUseCase
	@Inject lateinit var deleteDateNoteByIdUseCase: DeleteDateNoteByIdUseCase
	@Inject lateinit var getToDoListSortMethodIdUseCase: GetToDoListSortMethodIdUseCase
	@Inject lateinit var getSortedToDoListForDayUseCase: GetSortedToDoListForDayUseCase
	@Inject lateinit var clearToDoListForDayUseCase: ClearToDoListForDayUseCase
	@Inject lateinit var addToDoListItemUseCase: AddToDoListItemUseCase
	@Inject lateinit var deleteToDoListItemByIdUseCase: DeleteToDoListItemByIdUseCase
	@Inject lateinit var disableListItemNotificationByIdUseCase: DisableListItemNotificationByIdUseCase
	@Inject lateinit var enableListItemNotificationByIdUseCase: EnableListItemNotificationByIdUseCase
	@Inject lateinit var inverseListItemIsDoneByIdUseCase: InverseListItemIsDoneByIdUseCase
	@Inject lateinit var saveToDoListSortMethodIdUseCase: SaveToDoListSortMethodIdUseCase
	@Inject lateinit var getMemorableDatesForDayUseCase: GetMemorableDatesForDayUseCase
	@Inject lateinit var getAllMenstruationPeriodsUseCase: GetAllMenstruationPeriodsUseCase
	@Inject lateinit var getEstimatedNextMenstruationPeriodUseCase: GetEstimatedNextMenstruationPeriodUseCase
	@Inject lateinit var getWomanSectionEnabledUseCase: GetWomanSectionEnabledUseCase

	val title = day.toDateString()
	val toDoList by lazy { getSortedToDoListForDayUseCase(day).asLiveData() }
	val isToDoListVisible by lazy { toDoList.map { it.isNotEmpty() } }
	private val note by lazy { getDateNoteLiveDataUseCase(day).asLiveData() }
	val noteText by lazy { note.map { it?.text } }
	val isNoteVisible by lazy { note.map { it != null } }
	val newToDoListItemText = MutableLiveData("")
	val memorableDates by lazy { getMemorableDatesForDayUseCase(day).asLiveData() }
	val isMemorableDatesVisible by lazy { memorableDates.map { it.isNotEmpty() } }
	val isMenstruationIconVisible by lazy { getMenstruationIconVisibility() }
	val isEstimatedMenstruationIconVisible by lazy { getEstimatedMenstruationIconVisibility() }
	val isCalendarIconVisible by lazy { getCalendarIconVisibility() }
	val toDoListSortMethodId by lazy { getToDoListSortMethodIdUseCase().asLiveData() }
	val isDoListSortMethodDropDownVisible by lazy { toDoList.map { it.isNotEmpty() } }

	private val weatherForecast = MutableLiveData<WeatherForecast>()
	val weatherForecastForDate = weatherForecast.map { forecast ->
		forecast.items.find { day.isSameDay(it.dateInSeconds) }
	}
	val dayTemperature = weatherForecastForDate.map { it?.temperature?.dayString }
	val nightTemperature = weatherForecastForDate.map { it?.temperature?.nightString }

	val isWeatherForecastContainerVisible = weatherForecastForDate.map { it != null }
	val weatherForecastIconUrl = weatherForecastForDate.map { "" /*it?.weather?.firstOrNull()?.iconUrl*/ } // TODO
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
		val calendarDateScope = Toothpick.openScopes(
			APP_SCOPE,
			MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
			CALENDAR_DATE_VIEW_MODEL_SCOPE
		)
		Toothpick.inject(this, calendarDateScope)
	}

	private fun loadForecast() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val locationId = getLocationUseCase()?.id ?: throw NullPointerException()
				val forecast = getForecastForLocationIdUseCase(locationId)
				weatherForecast.postValue(forecast)
			} catch(e: Exception) {
				// TODO Message display temporarily removed
//				showMessage(Text.TextResource(R.string.failed_to_get_forecast))
			}
		}
	}

	private fun getMenstruationIconVisibility(): LiveData<Boolean> {
		return combine(
			getWomanSectionEnabledUseCase(),
			isDayOfMenstruationPeriod(day)
		) { isWomanSectionEnabled, isDayOfMenstruationPeriod ->
			isWomanSectionEnabled && isDayOfMenstruationPeriod
		}.asLiveData()
	}

	private fun isDayOfMenstruationPeriod(day: Day): Flow<Boolean> {
		return getAllMenstruationPeriodsUseCase().map { menstruationPeriods ->
			menstruationPeriods.find { day.isWithinInterval(it.startDate, it.endDate) } != null
		}
	}

	private fun getEstimatedMenstruationIconVisibility(): LiveData<Boolean> {
		return combine(
			getWomanSectionEnabledUseCase(),
			isDayOfEstimatedMenstruationPeriod(day)
		) { isWomanSectionEnabled, isDayOfEstimatedMenstruationPeriod ->
			isWomanSectionEnabled && isDayOfEstimatedMenstruationPeriod
		}.asLiveData()
	}

	private fun isDayOfEstimatedMenstruationPeriod(day: Day): Flow<Boolean> {
		return getEstimatedNextMenstruationPeriodUseCase().map { menstruationPeriod ->
			menstruationPeriod ?: return@map false
			day.isWithinInterval(menstruationPeriod.startDate, menstruationPeriod.endDate)
		}
	}

	private fun getCalendarIconVisibility(): LiveData<Boolean> {
		return combine(
			getWomanSectionEnabledUseCase(),
			isDayNotIncludedInMenstruationPeriod(day)
		) { isWomanSectionEnabled, dayNotIncludedInMenstruationPeriod ->
			!isWomanSectionEnabled || dayNotIncludedInMenstruationPeriod
		}.asLiveData()
	}

	private fun isDayNotIncludedInMenstruationPeriod(day: Day): Flow<Boolean> {
		return combine(
			isDayOfMenstruationPeriod(day),
			isDayOfEstimatedMenstruationPeriod(day)
		) { isDayOfMenstruationPeriod, isDayOfEstimatedMenstruationPeriod ->
			!isDayOfMenstruationPeriod && !isDayOfEstimatedMenstruationPeriod
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
				deleteDateNoteByIdUseCase(noteId)
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
				clearToDoListForDayUseCase(day)
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
				addToDoListItemUseCase(item)
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
				deleteToDoListItemByIdUseCase(itemId)
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
				disableListItemNotificationByIdUseCase(itemId)
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
				enableListItemNotificationByIdUseCase(itemId, time)
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
				inverseListItemIsDoneByIdUseCase(itemId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error_try_again_later))
			}
		}
	}

	fun onToDoListItemLongClick(item: ToDoListItem) {
		copyToClipboard(item.text)
		showMessage(Text.TextResource(R.string.text_copied))
	}

	fun onSortMethodSelected(dropDownItem: ToDoListSortMethodDropDownItem) {
		val sortMethod = dropDownItem.toSortMethod()
		saveToDoListSortMethod(sortMethod)
	}

	private fun saveToDoListSortMethod(sortMethod: ToDoListSortMethod) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				saveToDoListSortMethodIdUseCase(sortMethod.id)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error))
			}
		}
	}

	override fun onCleared() {
		Toothpick.closeScope(CALENDAR_DATE_VIEW_MODEL_SCOPE)
		super.onCleared()
	}

	class Factory(private val day: Day) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return CalendarDateViewModel(day) as T
		}
	}
}