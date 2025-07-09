
package com.example.alerthook

import android.app.Application
import android.content.ComponentName
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _isNotificationListenerEnabled = MutableStateFlow(false)
    val isNotificationListenerEnabled: StateFlow<Boolean> = _isNotificationListenerEnabled

    fun checkNotificationListenerStatus() {
        viewModelScope.launch {
            val cn = ComponentName(getApplication(), NotificationListener::class.java)
            val flat = Settings.Secure.getString(
                getApplication().contentResolver,
                "enabled_notification_listeners"
            )
            _isNotificationListenerEnabled.value = flat != null && flat.contains(cn.flattenToString())
        }
    }
}
