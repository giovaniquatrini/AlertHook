
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

    init {
        viewModelScope.launch {
            _webhookUrl.value = settingsManager.webhookUrl.first()
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
}
