
package com.example.alerthook

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val _allApps = MutableStateFlow<List<AppInfo>>(emptyList())
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    val appList: StateFlow<List<AppInfo>> = _allApps.combine(_searchText) { apps, text ->
        if (text.isBlank()) {
            apps
        } else {
            apps.filter {
                it.appName.contains(text, ignoreCase = true) ||
                it.packageName.contains(text, ignoreCase = true)
            }
        }
    }.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val settingsManager = SettingsManager(application.applicationContext)

    init {
        loadInstalledApps()
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            _isLoading.value = true
            val selectedAppPackageNames = settingsManager.selectedApps.first()

            val apps = withContext(Dispatchers.IO) {
                val packageManager = getApplication<Application>().packageManager
                val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                installedApps.map { appInfo ->
                    AppInfo(
                        packageName = appInfo.packageName,
                        appName = packageManager.getApplicationLabel(appInfo).toString(),
                        icon = packageManager.getApplicationIcon(appInfo),
                        isSelected = selectedAppPackageNames.contains(appInfo.packageName)
                    )
                }.sortedBy { it.appName.lowercase() }
            }
            _allApps.value = apps
            _isLoading.value = false
        }
    }

    fun toggleAppSelection(appInfo: AppInfo) {
        _allApps.value = _allApps.value.map { app ->
            if (app.packageName == appInfo.packageName) {
                app.copy(isSelected = !app.isSelected)
            } else {
                app
            }
        }
        saveSelectedApps()
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    private fun saveSelectedApps() {
        viewModelScope.launch {
            val currentlySelected = _allApps.value.filter { it.isSelected }.map { it.packageName }.toSet()
            settingsManager.saveSelectedApps(currentlySelected)
        }
    }
}
