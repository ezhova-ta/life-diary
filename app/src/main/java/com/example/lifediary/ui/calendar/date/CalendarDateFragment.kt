package com.example.lifediary.ui.calendar.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.CalendarEventListAdapter
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.ToDoListAdapter
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.databinding.FragmentCalendarDateBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.ui.calendar.date.ToDoListItemNotificationTimePickerFragment.Companion.PICKED_TIME_BUNDLE_KEY
import com.example.lifediary.ui.calendar.date.ToDoListItemNotificationTimePickerFragment.Companion.PICK_TIME_REQUEST_KEY
import com.example.lifediary.ui.calendar.date.ToDoListItemNotificationTimePickerFragment.ToDoListItemNotificationTime
import com.example.lifediary.utils.Day
import java.util.*

class CalendarDateFragment : BaseFragment() {
    override val viewModel: CalendarDateViewModel by viewModels(
        factoryProducer = { CalendarDateViewModel.Factory(getDayFromArguments()) }
    )
    private var _binding: FragmentCalendarDateBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_KEY = "com.example.lifediary.ui.calendar.DAY_KEY"
        private const val NOTE_VIEW_ROLLED_UP_MAX_LINES = 5

        fun getInstance(day: Day): Fragment {
            val fragment = CalendarDateFragment()
            fragment.arguments = Bundle().apply { putParcelable(DAY_KEY, day) }
            return fragment
        }
    }

    private fun getDayFromArguments(): Day {
        return requireArguments().getParcelable(DAY_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupNoteView()
        setupToDoListView()
        setupEventListView()
        setupAddToDoListItemInputView()
        setupClearToDOListConfirmationDialog()
        setupToDoListItemNotificationScheduling()
        return binding.root
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
            onDeleteItemClickListener = ListItemClickListener { viewModel.onDeleteToDoListItemClick(it) },
            onEnableNotificationClickListener = ListItemClickListener { viewModel.onToDoListItemNotificationClick(it) },
            onItemClickListener = ListItemClickListener { viewModel.onToDoListItemClick(it) },
            onItemLongClickListener = ListItemClickListener { viewModel.onToDoListItemLongClick(it) }
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
            calendarEventListAdapter.submitList(dateList)
        }
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
        showDefaultConfirmationDialog(
            messageRes = R.string.clear_to_do_list_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearToDoListConfirmed,
            onCancelled = viewModel::onClearToDoListCancelled
        )
    }

    private fun setupToDoListItemNotificationScheduling() {
        viewModel.toDoListItemScheduleNotificationEvent.observe(viewLifecycleOwner) { event ->
            val toDoListItem = event.getData() ?: return@observe
            scheduleNotification(
                toDoListItem,
                { timeInMillis ->
                    viewModel.onToDoListItemNotificationScheduled(toDoListItem, timeInMillis)
                },
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
                val notificationTime = Calendar.getInstance().apply {
                    set(Calendar.SECOND, 0)
                    set(Calendar.MINUTE, pickedTime.minute)
                    set(Calendar.HOUR_OF_DAY, pickedTime.hour)
                    set(Calendar.DATE, toDoListItem.day.dayNumber)
                    set(Calendar.MONTH, toDoListItem.day.monthNumber - 1)
                    set(Calendar.YEAR, toDoListItem.day.year)
                }
                notificationScheduler.scheduleNotification(toDoListItem, notificationTime.timeInMillis)
                onComplete(notificationTime)
            }

            clearFragmentResultListener(PICK_TIME_REQUEST_KEY) // TODO Needed?
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}