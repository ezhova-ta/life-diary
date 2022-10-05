package com.example.lifediary.data

import android.content.Context
import android.net.Uri
import com.example.lifediary.domain.models.BackupData
import com.example.lifediary.domain.repositories.*
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupFileManager @Inject constructor(
	private val context: Context,
	private val mainNotesRepository: MainNotesRepository,
	private val dateNoteRepository: DateNoteRepository,
	private val toDoListRepository: ToDoListRepository,
	private val shoppingListRepository: ShoppingListRepository,
	private val postAddressRepository: PostAddressRepository,
	private val memorableDatesRepository: MemorableDatesRepository,
	private val womanSectionRepository: WomanSectionRepository

) {
	@Throws(FileNotFoundException::class, IOException::class)
	suspend fun exportDataToBackupFile(fileUri: Uri) {
		val backupData = getApplicationData()

		// TODO Inappropriate blocking method call
		context.contentResolver.openFileDescriptor(fileUri, "w")?.use {
			FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
				fileOutputStream.write(backupData.toByteArray())
			}
		}
	}

	private suspend fun getApplicationData(): BackupData {
		return BackupData(
			mainNotesRepository.getAllNotes(),
			dateNoteRepository.getAllNotes(),
			toDoListRepository.getAllToDoLists(),
			shoppingListRepository.getShoppingList(),
			postAddressRepository.getAllAddresses(),
			memorableDatesRepository.getDates(),
			womanSectionRepository.getAllMenstruationPeriods()
		)
	}

	private fun BackupData.toByteArray(): ByteArray {
		return Gson().toJson(this).toByteArray()
	}

	@Throws(FileNotFoundException::class, JsonSyntaxException::class, JsonIOException::class)
	suspend fun importDataFromBackupFile(fileUri: Uri) {
		// TODO Inappropriate blocking method call
		context.contentResolver.openInputStream(fileUri)?.use { inputStream ->
			InputStreamReader(inputStream).use { inputStreamReader ->
				val backupData = Gson().fromJson(inputStreamReader, BackupData::class.java)
				saveApplicationData(backupData)
			}
		}
	}

	private suspend fun saveApplicationData(backupData: BackupData) {
		with(backupData) {
			mainNotesRepository.addNotes(mainNotes)
			dateNoteRepository.addAllNotes(dateNotes)
			toDoListRepository.addToDoList(toDoLists)
			shoppingListRepository.addShoppingList(shoppingList)
			postAddressRepository.addAllAddresses(postAddresses)
			memorableDatesRepository.addAllDates(memorableDates)
			womanSectionRepository.addAllMenstruationPeriods(menstruationPeriods)
		}
	}
}