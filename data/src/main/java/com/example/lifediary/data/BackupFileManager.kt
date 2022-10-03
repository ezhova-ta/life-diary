package com.example.lifediary.data

import android.content.Context
import android.net.Uri
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupFileManager @Inject constructor(
	private val context: Context,
	private val memorableDatesRepository: MemorableDatesRepository
) {
	@Throws(FileNotFoundException::class, IOException::class)
	suspend fun writeDataToBackupFile(fileUri: Uri) {
		val memorableDates = getMemorableDates()

		// TODO Inappropriate blocking method call
		context.contentResolver.openFileDescriptor(fileUri, "w")?.use {
			FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
				fileOutputStream.write(memorableDates.toByteArray())
			}
		}
	}

	private fun List<MemorableDate>.toByteArray(): ByteArray {
		return Gson().toJson(this).toByteArray()
	}

	// TODO Test data for saving to backup file
	private suspend fun getMemorableDates(): List<MemorableDate> {
		return memorableDatesRepository.getDates()
	}
}