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
	private val womanSectionRepository: WomanSectionRepository,
	private val weatherRepository: WeatherRepository

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
			mainNotes = mainNotesRepository.getAllNotes(),
			dateNotes = dateNoteRepository.getAllNotes(),
			toDoLists = toDoListRepository.getAllToDoLists(),
			shoppingList = shoppingListRepository.getShoppingList(),
			postAddresses = postAddressRepository.getAllAddresses(),
			memorableDates = memorableDatesRepository.getDates(),
			menstruationPeriods = womanSectionRepository.getAllMenstruationPeriods(),
			location = weatherRepository.getLocation()
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
				saveApplicationData(backupData.nullifyIdentifiers())
			}
		}
	}

	private fun BackupData.nullifyIdentifiers(): BackupData {
		return apply {
			mainNotes.forEach { mainNote -> mainNote.id = null }
			dateNotes.forEach { dateNote -> dateNote.id = null }
			toDoLists.forEach { toDoListItem -> toDoListItem.id = null }
			shoppingList.forEach { shoppingListItem -> shoppingListItem.id = null }
			postAddresses.forEach { postAddress -> postAddress.id = null }
			memorableDates.forEach { memorableDate -> memorableDate.id = null }
			menstruationPeriods.forEach { menstruationPeriod -> menstruationPeriod.id = null }
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
			location?.let { weatherRepository.saveLocation(it) }
		}
	}
}