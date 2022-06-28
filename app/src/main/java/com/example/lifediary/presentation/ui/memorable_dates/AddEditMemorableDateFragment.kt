package com.example.lifediary.presentation.ui.memorable_dates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentAddEditMemorableDateBinding
import com.example.lifediary.presentation.ui.BaseFragment
import com.example.lifediary.presentation.DayNumberDropDownItem
import com.example.lifediary.presentation.MonthDropDownItem

class AddEditMemorableDateFragment : BaseFragment() {
	override val viewModel: AddEditMemorableDateViewModel by viewModels(
		factoryProducer = { AddEditMemorableDateViewModel.Factory(getDateIdFromArguments()) }
	)

	private var _binding: FragmentAddEditMemorableDateBinding? = null
	private val binding get() = _binding!!

	companion object {
		private const val DATE_ID_KEY = "lifediary.presentation.ui.memorable_dates.NOTE_ID_KEY"
		private const val DEFAULT_DATE_ID = 0L

		fun getInstance(noteId: Long?): AddEditMemorableDateFragment {
			val fragment = AddEditMemorableDateFragment()
			fragment.arguments = Bundle().apply{ putLong(DATE_ID_KEY, noteId ?: DEFAULT_DATE_ID) }
			return fragment
		}
	}

	private fun getDateIdFromArguments(): Long? {
		val noteId = requireArguments().getLong(DATE_ID_KEY)
		if(noteId == DEFAULT_DATE_ID) return null
		return noteId
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAddEditMemorableDateBinding.inflate(inflater, container, false)
		binding.lifecycleOwner = viewLifecycleOwner
		binding.viewModel = viewModel
		setupViews()
		return binding.root
	}

	private fun setupViews() {
		setupDayNumberDropDown()
		setupMonthDropDown()
	}

	private fun setupDayNumberDropDown() {
		val adapter = ArrayAdapter(
			requireContext(),
			R.layout.day_number_spinner_item,
			DayNumberDropDownItem.allElements
		)
		binding.dayNumberDropDown.adapter = adapter

		binding.dayNumberDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				val dayNumber = DayNumberDropDownItem.getFromPosition(position)
				viewModel.onDayNumberSelected(dayNumber)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {}
		}

		viewModel.existingDateDayNumber.observe(viewLifecycleOwner) { dayNumber ->
			val position = DayNumberDropDownItem.getPosition(dayNumber)
			binding.dayNumberDropDown.setSelection(position)
		}
	}

	private fun setupMonthDropDown() {
		val adapter = ArrayAdapter(
			requireContext(),
			R.layout.month_spinner_item,
			MonthDropDownItem.getAllStrings(requireContext())
		)
		binding.monthDropDown.adapter = adapter

		binding.monthDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				val month = MonthDropDownItem.getFromPosition(position)
				viewModel.onMonthSelected(month)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {}
		}

		viewModel.existingDateMonthNumber.observe(viewLifecycleOwner) { monthNumber ->
			val position = MonthDropDownItem.getPositionFromNumber(monthNumber)
			binding.monthDropDown.setSelection(position)
		}
	}
 }