package com.example.lifediary.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonDataStoreManager @Inject constructor(private val context: Context) {
	private val Context.commonDataStore: DataStore<Preferences> by preferencesDataStore(
		name = "common_data_store"
	)

	private val commonDataStore
		get() = context.commonDataStore

	val shoppingListSortMethodId = commonDataStore.data.map { preferences ->
		preferences[SHOPPING_LIST_SORT_METHOD_ID]
	}

	val toDoListSortMethodId = commonDataStore.data.map { preferences ->
		preferences[TO_DO_LIST_SORT_METHOD_ID]
	}

	companion object {
		val SHOPPING_LIST_SORT_METHOD_ID = intPreferencesKey(
			"com.example.lifediary.data.SHOPPING_LIST_SORT_METHOD_ID"
		)

		val TO_DO_LIST_SORT_METHOD_ID = intPreferencesKey(
			"com.example.lifediary.data.TO_DO_LIST_SORT_METHOD_ID"
		)
	}

	suspend fun setShoppingListSortMethodId(id: Int) {
		commonDataStore.edit { preferences ->
			preferences[SHOPPING_LIST_SORT_METHOD_ID] = id
		}
	}

	suspend fun setToDoListSortMethodId(id: Int) {
		commonDataStore.edit { preferences ->
			preferences[TO_DO_LIST_SORT_METHOD_ID] = id
		}
	}
}