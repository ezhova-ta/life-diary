package com.example.lifediary.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStoreManager @Inject constructor(private val context: Context) {
    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "settings_data_store",
        corruptionHandler = ReplaceFileCorruptionHandler {
            preferencesOf(
                IS_SHOPPING_LIST_SECTION_ENABLED_KEY to IS_SHOPPING_LIST_SECTION_ENABLED_DEFAULT_VALUE,
                IS_POST_ADDRESSES_SECTION_ENABLED_KEY to IS_POST_ADDRESSES_SECTION_ENABLED_DEFAULT_VALUE,
                IS_MEMORABLE_DATES_SECTION_ENABLED_KEY to IS_MEMORABLE_DATES_SECTION_ENABLED_DEFAULT_VALUE
            )
        }
    )

    private val settingsDataStore
        get() = context.settingsDataStore

    val isShoppingListSectionEnabled = settingsDataStore.data.map { preferences ->
        preferences[IS_SHOPPING_LIST_SECTION_ENABLED_KEY] ?: IS_SHOPPING_LIST_SECTION_ENABLED_DEFAULT_VALUE
    }

    val isPostAddressesSectionEnabled = settingsDataStore.data.map { preferences ->
        preferences[IS_POST_ADDRESSES_SECTION_ENABLED_KEY] ?: IS_POST_ADDRESSES_SECTION_ENABLED_DEFAULT_VALUE
    }

    val isMemorableDatesSectionEnabled = settingsDataStore.data.map { preferences ->
        preferences[IS_MEMORABLE_DATES_SECTION_ENABLED_KEY] ?: IS_MEMORABLE_DATES_SECTION_ENABLED_DEFAULT_VALUE
    }

    val isWomanSectionEnabled = settingsDataStore.data.map { preferences ->
        preferences[IS_WOMAN_SECTION_ENABLED_KEY] ?: IS_WOMAN_SECTION_ENABLED_DEFAULT_VALUE
    }

    companion object {
        val IS_SHOPPING_LIST_SECTION_ENABLED_KEY = booleanPreferencesKey(
            "com.example.lifediary.data.IS_SHOPPING_LIST_SECTION_ENABLED"
        )
        val IS_POST_ADDRESSES_SECTION_ENABLED_KEY = booleanPreferencesKey(
            "com.example.lifediary.data.IS_POST_ADDRESSES_SECTION_ENABLED"
        )
        val IS_MEMORABLE_DATES_SECTION_ENABLED_KEY = booleanPreferencesKey(
            "com.example.lifediary.data.IS_MEMORABLE_DATES_SECTION_ENABLED_KEY"
        )
        val IS_WOMAN_SECTION_ENABLED_KEY = booleanPreferencesKey(
            "com.example.lifediary.data.IS_WOMAN_SECTION_ENABLED_KEY"
        )
        const val IS_SHOPPING_LIST_SECTION_ENABLED_DEFAULT_VALUE = true
        const val IS_POST_ADDRESSES_SECTION_ENABLED_DEFAULT_VALUE = true
        const val IS_MEMORABLE_DATES_SECTION_ENABLED_DEFAULT_VALUE = true
        const val IS_WOMAN_SECTION_ENABLED_DEFAULT_VALUE = false
    }

    suspend fun setShoppingListSectionEnabled(isEnabled: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[IS_SHOPPING_LIST_SECTION_ENABLED_KEY] = isEnabled
        }
    }

    suspend fun setPostAddressesSectionEnabled(isEnabled: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[IS_POST_ADDRESSES_SECTION_ENABLED_KEY] = isEnabled
        }
    }

    suspend fun setMemorableDatesSectionEnabled(isEnabled: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[IS_MEMORABLE_DATES_SECTION_ENABLED_KEY] = isEnabled
        }
    }

    suspend fun setWomanSectionEnabled(isEnabled: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[IS_WOMAN_SECTION_ENABLED_KEY] = isEnabled
        }
    }
}