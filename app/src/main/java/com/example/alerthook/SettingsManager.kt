package com.example.alerthook

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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

        // Payload customization keys
        val INCLUDE_PACKAGE_NAME_KEY = booleanPreferencesKey("include_package_name")
        val INCLUDE_APP_NAME_KEY = booleanPreferencesKey("include_app_name")
        val INCLUDE_TITLE_KEY = booleanPreferencesKey("include_title")
        val INCLUDE_TEXT_KEY = booleanPreferencesKey("include_text")
        val INCLUDE_TIMESTAMP_KEY = booleanPreferencesKey("include_timestamp")
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

    // Payload customization preferences
    val includePackageName: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[INCLUDE_PACKAGE_NAME_KEY] ?: true // Default to true
        }

    suspend fun setIncludePackageName(include: Boolean) {
        context.dataStore.edit { settings ->
            settings[INCLUDE_PACKAGE_NAME_KEY] = include
        }
    }

    val includeAppName: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[INCLUDE_APP_NAME_KEY] ?: true // Default to true
        }

    suspend fun setIncludeAppName(include: Boolean) {
        context.dataStore.edit { settings ->
            settings[INCLUDE_APP_NAME_KEY] = include
        }
    }

    val includeTitle: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[INCLUDE_TITLE_KEY] ?: true // Default to true
        }

    suspend fun setIncludeTitle(include: Boolean) {
        context.dataStore.edit { settings ->
            settings[INCLUDE_TITLE_KEY] = include
        }
    }

    val includeText: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[INCLUDE_TEXT_KEY] ?: true // Default to true
        }

    suspend fun setIncludeText(include: Boolean) {
        context.dataStore.edit { settings ->
            settings[INCLUDE_TEXT_KEY] = include
        }
    }

    val includeTimestamp: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[INCLUDE_TIMESTAMP_KEY] ?: true // Default to true
        }

    suspend fun setIncludeTimestamp(include: Boolean) {
        context.dataStore.edit { settings ->
            settings[INCLUDE_TIMESTAMP_KEY] = include
        }
    }
}