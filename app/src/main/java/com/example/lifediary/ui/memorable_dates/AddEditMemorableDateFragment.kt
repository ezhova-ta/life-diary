package com.example.lifediary.ui.memorable_dates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentAddEditMemorableDateBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.MonthDropDownItem

class AddEditMemorableDateFragment : BaseFragment() {
	override val viewModel: AddEditMemorableDateViewModel by viewModels(
		factoryProducer = { AddEditMemorableDateViewModel.Factory(getDateIdFromArguments()) }
	)

	private var _binding: FragmentAddEditMemorableDateBinding? = null
	private val binding get() = _binding!!

	companion object {
		const val DATE_ID_KEY = "com.example.lifediary.ui.main.notes.NOTE_ID_KEY"
		const val DEFAULT_DATE_ID_VALUE = 0L

		fun getInstance(noteId: Long?): AddEditMemorableDateFragment {
			val fragment = AddEditMemorableDateFragment()
			fragment.arguments = Bundle().apply{ putLong(DATE_ID_KEY, noteId ?: DEFAULT_DATE_ID_VALUE) }
			return fragment
		}
	}

	private fun getDateIdFromArguments(): Long? {
		val noteId = requireArguments().getLong(DATE_ID_KEY)
		if(noteId == DEFAULT_DATE_ID_VALUE) return null
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
		setupMonthDropDown()
		return binding.root
	}

	private fun setupMonthDropDown() {
		val adapter = ArrayAdapter(
			requireContext(),
			R.layout.month_spinner_item,
			MonthDropDownItem.getAllStrings(requireContext())
		)
		adapter.setDropDownViewResource(R.layout.month_spinner_item)
		binding.monthDropDown.adapter = adapter

		binding.monthDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				val month = MonthDropDownItem.getFromPosition(position) ?: return
				viewModel.onMonthSelected(month)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {}
		}
	}
 }