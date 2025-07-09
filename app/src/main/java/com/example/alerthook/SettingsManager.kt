
package com.example.alerthook

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanSetPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    companion object {
        val WEBHOOK_URL_KEY = stringPreferencesKey("webhook_url")
        val SELECTED_APPS_KEY = booleanSetPreferencesKey("selected_apps")
    }

    val webhookUrl: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[WEBHOOK_URL_KEY] ?: ""
        }

    suspend fun saveWebhookUrl(url: String) {
        context.dataStore.edit { settings ->
            settings[WEBHOOK_URL_KEY] = url
        }
    }

    val selectedApps: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[SELECTED_APPS_KEY] ?: emptySet()
        }

    suspend fun saveSelectedApps(packageNames: Set<String>) {
        context.dataStore.edit { settings ->
            settings[SELECTED_APPS_KEY] = packageNames
        }
    }
}
