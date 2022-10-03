package com.example.lifediary.presentation.ui.settings

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentSettingsBinding
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.presentation.ui.BaseFragment
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SettingsFragment : BaseFragment() {
    override val viewModel: SettingsViewModel by viewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val requestCreateBackupFile = registerForActivityResult(
        ActivityResultContracts.CreateDocument()
    ) { fileUri -> writeDataToBackupFile(fileUri) }

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
        setupSaveDataButton()
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

    private fun setupSaveDataButton() {
        binding.saveDataLink.setOnClickListener { onSaveDataClick() }
    }

    private fun onSaveDataClick() {
        val backupFileName = "$BACKUP_FILE_NAME.$BACKUP_FILE_EXTENSION"
        requestCreateBackupFile.launch(backupFileName)
    }

    private fun writeDataToBackupFile(fileUri: Uri) {
        val memorableDates = getMemorableDates()

        try {
            requireContext().contentResolver.openFileDescriptor(fileUri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
                    fileOutputStream.write(
                        Gson().toJson(memorableDates).toByteArray()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            Log.d("write_to_backup_file_debug", "FileNotFoundException")
        } catch (e: IOException) {
            Log.d("write_to_backup_file_debug", "IOException")
        }
    }

    // TODO Test data for saving to backup file
    private fun getMemorableDates(): List<MemorableDate> {
        return listOf(
            MemorableDate(null, "День солёного огурца", 1, 5),
            MemorableDate(null, "День квашеной капусты", 10, 8),
            MemorableDate(null, "День гранёного стакана", 6, 12)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}