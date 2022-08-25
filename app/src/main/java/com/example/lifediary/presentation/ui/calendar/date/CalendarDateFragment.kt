package com.example.lifediary.presentation.ui.calendar.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentCalendarDateBinding
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.CALENDAR_DATE_FRAGMENT_SCOPE
import com.example.lifediary.di.DiScopes.CALENDAR_DATE_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.utils.CalendarBuilder
import com.example.lifediary.presentation.NotificationScheduler
import com.example.lifediary.presentation.adapters.CalendarEventListAdapter
import com.example.lifediary.presentation.adapters.ListItemClickListener
import com.example.lifediary.presentation.adapters.ToDoListAdapter
import com.example.lifediary.presentation.models.CalendarEvent
import com.example.lifediary.presentation.models.MemorableDateToCalendarEventAdapter
import com.example.lifediary.presentation.models.dropdowns.ToDoListSortMethodDropDownItem
import com.example.lifediary.presentation.ui.BaseFragment
import com.example.lifediary.presentation.ui.calendar.date.ToDoListItemNotificationTimePickerFragment.Companion.PICKED_TIME_BUNDLE_KEY
import com.example.lifediary.presentation.ui.calendar.date.ToDoListItemNotificationTimePickerFragment.Companion.PICK_TIME_REQUEST_KEY
import com.example.lifediary.presentation.ui.calendar.date.ToDoListItemNotificationTimePickerFragment.ToDoListItemNotificationTime
import com.example.lifediary.presentation.utils.UiConstants.Calendar.NOTE_VIEW_ROLLED_UP_MAX_LINES
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class CalendarDateFragment : BaseFragment() {
    @Inject lateinit var notificationScheduler: NotificationScheduler
    override val viewModel: CalendarDateViewModel by viewModels(
        factoryProducer = { CalendarDateViewModel.Factory(getDayFromArguments()) }
    )
    private var _binding: FragmentCalendarDateBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_NUMBER_KEY = "lifediary.presentation.ui.calendar.date.DAY_NUMBER_KEY"
        private const val MONTH_NUMBER_KEY = "lifediary.presentation.ui.calendar.date.MONTH_NUMBER_KEY"
        private const val YEAR_KEY = "presentation.ui.calendar.date.EAR_KEY"

        fun getInstance(day: Day): Fragment {
            val fragment = CalendarDateFragment()
            fragment.arguments = Bundle().apply {
                putInt(DAY_NUMBER_KEY, day.dayNumber)
                putInt(MONTH_NUMBER_KEY, day.monthNumber)
                putInt(YEAR_KEY, day.year)
            }
            return fragment
        }
    }

    private fun getDayFromArguments(): Day {
        val dayNumber = requireArguments().getInt(DAY_NUMBER_KEY)
        val monthNumber = requireArguments().getInt(MONTH_NUMBER_KEY)
        val year = requireArguments().getInt(YEAR_KEY)
        return Day(dayNumber, monthNumber, year)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope()
    }

    private fun bindScope() {
        val scope = Toothpick.openScopes(
            APP_SCOPE,
            MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
            CALENDAR_DATE_VIEW_MODEL_SCOPE,
            CALENDAR_DATE_FRAGMENT_SCOPE
        )
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        setupNoteView()
        setupToDoListView()
        setupEventListView()
        setupAddToDoListItemInputView()
        setupClearToDOListConfirmationDialog()
        setupDeleteNoteConfirmationDialog()
        setupToDoListItemNotificationScheduling()
        setupSortMethodDropDown()
    }

    private fun setupNoteView() {
        binding.noteView.rolledUpMaxLines = NOTE_VIEW_ROLLED_UP_MAX_LINES
        binding.noteView.setOnLongClickListener {
            viewModel.onNoteLongClick()
            true
        }
    }

    private fun setupToDoListView() {
        val toDoListAdapter = ToDoListAdapter(
            ListItemClickListener { viewModel.onDeleteToDoListItemClick(it) },
            ListItemClickListener { viewModel.onToDoListItemNotificationClick(it) },
            ListItemClickListener { viewModel.onToDoListItemClick(it) },
            ListItemClickListener { viewModel.onToDoListItemLongClick(it) }
        )
        binding.toDoListView.adapter = toDoListAdapter
        viewModel.toDoList.observe(viewLifecycleOwner) { toDoList ->
            toDoListAdapter.submitList(toDoList)
        }
    }

    private fun setupEventListView() {
        val calendarEventListAdapter = CalendarEventListAdapter()
        binding.eventListView.adapter = calendarEventListAdapter
        viewModel.memorableDates.observe(viewLifecycleOwner) { dateList ->
            calendarEventListAdapter.submitList(dateList.toCalendarEventList())
        }
    }

    private fun List<MemorableDate>.toCalendarEventList(): List<CalendarEvent> {
        return map { it.toCalendarEvent() }
    }

    private fun MemorableDate.toCalendarEvent(): CalendarEvent {
        return MemorableDateToCalendarEventAdapter(this)
    }

    private fun setupAddToDoListItemInputView() {
        binding.addToDoListItemInput.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.addToDoListItemInput.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onAddToDoListItemInputDone()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun setupClearToDOListConfirmationDialog() {
        viewModel.showClearToDoListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearToDOListConfirmationDialog()
        }
    }

    private fun showClearToDOListConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.clear_to_do_list_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearToDoListConfirmed,
            onCancelled = viewModel::onClearToDoListCancelled
        )
    }

    private fun setupDeleteNoteConfirmationDialog() {
        viewModel.showDeleteNoteConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showDeleteNoteConfirmationDialog()
        }
    }

    private fun showDeleteNoteConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.delete_note_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onDeleteNoteConfirmed,
            onCancelled = viewModel::onDeleteNoteCancelled
        )
    }

    private fun setupToDoListItemNotificationScheduling() {
        viewModel.toDoListItemScheduleNotificationEvent.observe(viewLifecycleOwner) { event ->
            val toDoListItem = event.getData() ?: return@observe
            scheduleNotification(
                toDoListItem,
                { time -> viewModel.onToDoListItemNotificationScheduled(toDoListItem, time) },
                { viewModel.onSchedulingToDoListItemNotificationCancelled(toDoListItem) }
            )
        }

        viewModel.toDoListItemCancelScheduledNotificationEvent.observe(viewLifecycleOwner) { event ->
            val toDoListItem = event.getData() ?: return@observe
            cancelScheduledNotification(toDoListItem)
        }
    }

    private fun scheduleNotification(
		toDoListItem: ToDoListItem,
		onComplete: (time: Calendar) -> Unit,
		onCancelled: () -> Unit
    ) {
        setNotificationTimePickerResultListener(toDoListItem, onComplete, onCancelled)
        showToDoListItemNotificationTimePicker()
    }

    private fun setNotificationTimePickerResultListener(
		toDoListItem: ToDoListItem,
		onComplete: (time: Calendar) -> Unit,
		onCancelled: () -> Unit
    ) {
        setFragmentResultListener(PICK_TIME_REQUEST_KEY) { _, bundle ->
            val pickedTime: ToDoListItemNotificationTime? = bundle.getParcelable(PICKED_TIME_BUNDLE_KEY)

            if(pickedTime == null) {
                onCancelled()
            } else {
                val notificationTime = CalendarBuilder()
                    .setDayOfMonth(toDoListItem.day.dayNumber)
                    .setMonthNumber(toDoListItem.day.monthNumber)
                    .setYearNumber(toDoListItem.day.year)
                    .setHourOfDay(pickedTime.hour)
                    .setMinutes(pickedTime.minute)
                    .setSeconds(0)
                    .build()

                notificationScheduler.scheduleNotification(toDoListItem, notificationTime.timeInMillis)
                onComplete(notificationTime)
            }

            // TODO Needed?
            clearFragmentResultListener(PICK_TIME_REQUEST_KEY)
        }
    }

    private fun showToDoListItemNotificationTimePicker() {
        ToDoListItemNotificationTimePickerFragment().show(
            requireActivity().supportFragmentManager,
            ToDoListItemNotificationTimePickerFragment.TAG
        )
    }

    private fun cancelScheduledNotification(toDoListItem: ToDoListItem) {
        notificationScheduler.cancelScheduledNotification(toDoListItem)
    }

    private fun setupSortMethodDropDown() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.sort_method_spinner_item,
            ToDoListSortMethodDropDownItem.getAllStrings(requireContext())
        )

        binding.sortMethodDropDown.adapter = adapter

        binding.sortMethodDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sortMethod = ToDoListSortMethodDropDownItem.getFromPosition(position)
                viewModel.onSortMethodSelected(sortMethod)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.toDoListSortMethodId.observe(viewLifecycleOwner) { sortMethodId ->
            sortMethodId ?: return@observe
            val position = ToDoListSortMethodDropDownItem.getPositionFromId(sortMethodId)
            binding.sortMethodDropDown.setSelection(position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        Toothpick.closeScope(CALENDAR_DATE_FRAGMENT_SCOPE)
        super.onDestroy()
    }
}