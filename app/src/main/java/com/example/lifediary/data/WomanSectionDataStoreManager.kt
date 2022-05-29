package com.example.lifediary.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import com.example.lifediary.utils.UiConstants
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WomanSectionDataStoreManager @Inject constructor(private val context: Context) {
	private val Context.womanSectionDataStore: DataStore<Preferences> by preferencesDataStore(
		name = "woman_section_data_store",
		corruptionHandler = ReplaceFileCorruptionHandler {
			preferencesOf(
				DURATION_OF_MENSTRUAL_CYCLE_KEY to DURATION_OF_MENSTRUAL_CYCLE_DEFAULT_VALUE
			)
		}
	)

	private val womanSectionDataStore
		get() = context.womanSectionDataStore

	val durationOfMenstrualCycle = womanSectionDataStore.data.map { preferences ->
		preferences[DURATION_OF_MENSTRUAL_CYCLE_KEY] ?: DURATION_OF_MENSTRUAL_CYCLE_DEFAULT_VALUE
	}

	val durationOfMenstruationPeriod = womanSectionDataStore.data.map { preferences ->
		preferences[DURATION_OF_MENSTRUATION_PERIOD_KEY] ?: DURATION_OF_MENSTRUATION_PERIOD_DEFAULT_VALUE
	}

	companion object {
		val DURATION_OF_MENSTRUAL_CYCLE_KEY = intPreferencesKey(
			"com.example.lifediary.data.DURATION_OF_MENSTRUAL_CYCLE_KEY"
		)

		val DURATION_OF_MENSTRUATION_PERIOD_KEY = intPreferencesKey(
			"com.example.lifediary.data.DURATION_OF_MENSTRUATION_PERIOD_KEY"
		)

		const val DURATION_OF_MENSTRUAL_CYCLE_DEFAULT_VALUE =
			UiConstants.WomanSection.DEFAULT_DURATION_OF_MENSTRUAL_CYCLE

		const val DURATION_OF_MENSTRUATION_PERIOD_DEFAULT_VALUE =
			UiConstants.WomanSection.DEFAULT_DURATION_OF_MENSTRUATION_PERIOD
	}

	suspend fun setDurationOfMenstrualCycle(value: Int) {
		womanSectionDataStore.edit { preferences ->
			preferences[DURATION_OF_MENSTRUAL_CYCLE_KEY] = value
		}
	}

	suspend fun setDurationOfMenstruationPeriod(value: Int) {
		womanSectionDataStore.edit { preferences ->
			preferences[DURATION_OF_MENSTRUATION_PERIOD_KEY] = value
		}
	}
}