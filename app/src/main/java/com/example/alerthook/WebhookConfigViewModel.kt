package com.example.alerthook

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WebhookConfigViewModel(application: Application) : AndroidViewModel(application) {

    private val _webhookUrl = MutableStateFlow("")
    val webhookUrl: StateFlow<String> = _webhookUrl

    private val _saveStatus = MutableStateFlow<String?>(null)
    val saveStatus: StateFlow<String?> = _saveStatus

    private val settingsManager = SettingsManager(application.applicationContext)
    private val webhookService = WebhookService()

    // Payload customization states
    private val _includePackageName = MutableStateFlow(true)
    val includePackageName: StateFlow<Boolean> = _includePackageName

    private val _includeAppName = MutableStateFlow(true)
    val includeAppName: StateFlow<Boolean> = _includeAppName

    private val _includeTitle = MutableStateFlow(true)
    val includeTitle: StateFlow<Boolean> = _includeTitle

    private val _includeText = MutableStateFlow(true)
    val includeText: StateFlow<Boolean> = _includeText

    private val _includeTimestamp = MutableStateFlow(true)
    val includeTimestamp: StateFlow<Boolean> = _includeTimestamp

    init {
        viewModelScope.launch {
            _webhookUrl.value = settingsManager.webhookUrl.first()
            _includePackageName.value = settingsManager.includePackageName.first()
            _includeAppName.value = settingsManager.includeAppName.first()
            _includeTitle.value = settingsManager.includeTitle.first()
            _includeText.value = settingsManager.includeText.first()
            _includeTimestamp.value = settingsManager.includeTimestamp.first()
        }
    }

    fun onWebhookUrlChange(newUrl: String) {
        _webhookUrl.value = newUrl
        _saveStatus.value = null // Clear status when URL changes
    }

    fun saveWebhookUrl() {
        viewModelScope.launch {
            val url = _webhookUrl.value
            if (url.isNotBlank()) {
                if (Patterns.WEB_URL.matcher(url).matches()) {
                    settingsManager.saveWebhookUrl(url)
                    _saveStatus.value = "URL do Webhook salva com sucesso!"
                } else {
                    _saveStatus.value = "URL inválida. Por favor, insira uma URL válida."
                }
            } else {
                _saveStatus.value = "A URL do Webhook não pode estar vazia."
            }
        }
    }

    fun testWebhook() {
        viewModelScope.launch {
            val url = _webhookUrl.value
            if (url.isNotBlank() && Patterns.WEB_URL.matcher(url).matches()) {
                _saveStatus.value = "Testando conexão com o Webhook..."
                val success = webhookService.testWebhook(url)
                if (success) {
                    _saveStatus.value = "Teste do Webhook concluído: SUCESSO!"
                } else {
                    _saveStatus.value = "Teste do Webhook concluído: FALHA. Verifique a URL e sua conexão."
                }
            } else {
                _saveStatus.value = "Por favor, insira uma URL válida antes de testar."
            }
        }
    }

    fun toggleIncludePackageName(checked: Boolean) {
        viewModelScope.launch { settingsManager.setIncludePackageName(checked) }
        _includePackageName.value = checked
    }

    fun toggleIncludeAppName(checked: Boolean) {
        viewModelScope.launch { settingsManager.setIncludeAppName(checked) }
        _includeAppName.value = checked
    }

    fun toggleIncludeTitle(checked: Boolean) {
        viewModelScope.launch { settingsManager.setIncludeTitle(checked) }
        _includeTitle.value = checked
    }

    fun toggleIncludeText(checked: Boolean) {
        viewModelScope.launch { settingsManager.setIncludeText(checked) }
        _includeText.value = checked
    }

    fun toggleIncludeTimestamp(checked: Boolean) {
        viewModelScope.launch { settingsManager.setIncludeTimestamp(checked) }
        _includeTimestamp.value = checked
    }
}