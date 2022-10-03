package com.example.lifediary.presentation.ui.settings

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentSettingsBinding
import com.example.lifediary.presentation.ui.BaseFragment

class SettingsFragment : BaseFragment() {
    override val viewModel: SettingsViewModel by viewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val requestCreateBackupFile = registerForActivityResult(
        ActivityResultContracts.CreateDocument()
    ) { fileUri -> viewModel.onBackupFileCreated(fileUri) }

    companion object {
        private const val BACKUP_FILE_NAME = "life_diary_backup"
        private const val BACKUP_FILE_EXTENSION = "json"

        fun getInstance(): Fragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        setupSwitches()
        setupClearCalendarNotesConfirmationDialog()
        setupClearToDoListsConfirmationDialog()
        setupBackupButtons()
    }

    private fun setupSwitches() {
        binding.shoppingListSectionSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onShoppingListSectionEnabledChanged(isChecked)
        }

        binding.postAddressesSectionSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onPostAddressesSectionEnabledChanged(isChecked)
        }

        binding.memorableDatesSectionSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onMemorableDatesSectionEnabledChanged(isChecked)
        }
        binding.womanSectionSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onWomenSectionEnabledChanged(isChecked)
        }
    }

    private fun setupClearCalendarNotesConfirmationDialog() {
        viewModel.showClearCalendarNotesConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearCalendarNotesConfirmationDialog()
        }
    }

    private fun showClearCalendarNotesConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.all_calendar_notes_will_be_cleared,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearCalendarNotesConfirmed,
            onCancelled = viewModel::onClearCalendarNotesCancelled
        )
    }

    private fun setupClearToDoListsConfirmationDialog() {
        viewModel.showClearToDoListsConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearToDoListsConfirmationDialog()
        }
    }

    private fun showClearToDoListsConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.all_to_do_lists_will_be_cleared,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearToDoListsConfirmed,
            onCancelled = viewModel::onClearToDoListsCancelled
        )
    }

    private fun setupBackupButtons() {
        binding.exportDataLink.setOnClickListener { onExportDataClick() }
        binding.importDataLink.setOnClickListener { onImportDataClick() }
    }

    private fun onExportDataClick() {
        val backupFileName = "$BACKUP_FILE_NAME.$BACKUP_FILE_EXTENSION"
        requestCreateBackupFile.launch(backupFileName)
    }

    private fun onImportDataClick() {
        TODO("Do not implemented yet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}