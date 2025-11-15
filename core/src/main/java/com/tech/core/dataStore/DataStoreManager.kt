package com.tech.core.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "chillit_prefs")


class DataStoreManager(private val context: Context) {


    val themeMode = context.dataStore.data.map { prefs ->
        prefs[PreferencesKeys.THEME_MODE] ?: "light"
    }


    suspend fun saveThemeMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.THEME_MODE] = mode
        }
    }
}