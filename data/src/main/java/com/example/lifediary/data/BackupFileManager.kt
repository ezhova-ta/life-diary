package com.example.lifediary.data

import android.content.Context
import android.net.Uri
import com.example.lifediary.domain.repositories.*
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintStream
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
	private companion object {
		const val BACKUP_JSON_KEY_MAIN_NOTES = "mainNotes"
		const val BACKUP_JSON_KEY_DATE_NOTES = "dateNotes"
		const val BACKUP_JSON_KEY_TO_DO_LISTS = "toDoLists"
		const val BACKUP_JSON_KEY_SHOPPING_LIST = "shoppingList"
		const val BACKUP_JSON_KEY_POST_ADDRESSES = "postAddresses"
		const val BACKUP_JSON_KEY_MEMORABLE_DATES = "memorableDates"
		const val BACKUP_JSON_KEY_MENSTRUATION_PERIODS = "menstruationPeriods"
	}

	@Throws(FileNotFoundException::class, IOException::class)
	suspend fun writeDataToBackupFile(fileUri: Uri) {
		val data = getApplicationData()

		// TODO Inappropriate blocking method call
		context.contentResolver.openFileDescriptor(fileUri, "w")?.use {
			FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
				fileOutputStream.write(data.toByteArray())
			}
		}
	}

	private suspend fun getApplicationData(): Map<String, Any> {
		val mainNotes = mainNotesRepository.getAllNotes()
		val dateNotes = dateNoteRepository.getAllNotes()
		val toDoLists = toDoListRepository.getAllToDoLists()
		val shoppingList = shoppingListRepository.getShoppingList()
		val postAddresses = postAddressRepository.getAllAddresses()
		val memorableDates = memorableDatesRepository.getDates()
		val menstruationPeriods = womanSectionRepository.getAllMenstruationPeriods()

		return mapOf(
			Pair(BACKUP_JSON_KEY_MAIN_NOTES, mainNotes),
			Pair(BACKUP_JSON_KEY_DATE_NOTES, dateNotes),
			Pair(BACKUP_JSON_KEY_TO_DO_LISTS, toDoLists),
			Pair(BACKUP_JSON_KEY_SHOPPING_LIST, shoppingList),
			Pair(BACKUP_JSON_KEY_POST_ADDRESSES, postAddresses),
			Pair(BACKUP_JSON_KEY_MEMORABLE_DATES, memorableDates),
			Pair(BACKUP_JSON_KEY_MENSTRUATION_PERIODS, menstruationPeriods)
		)
	}

	private fun Map<String, Any>.toByteArray(): ByteArray {
		return Gson().toJson(this).toByteArray()
	}
}