package com.example.alerthook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alerthook.ui.theme.AlertHookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebhookConfigScreen(navController: NavController, viewModel: WebhookConfigViewModel = viewModel()) {
    val webhookUrl by viewModel.webhookUrl.collectAsState()
    val saveStatus by viewModel.saveStatus.collectAsState()

    AlertHookTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Configuração do Webhook") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = webhookUrl,
                        onValueChange = { viewModel.onWebhookUrlChange(it) },
                        label = { Text("URL do Webhook") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    ElevatedButton(
                        onClick = { viewModel.saveWebhookUrl() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Salvar Configuração")
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.testWebhook() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Testar Webhook")
                    }

                    saveStatus?.let {
                        Spacer(Modifier.height(16.dp))
                        Text(it, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WebhookConfigScreenPreview() {
    WebhookConfigScreen(navController = rememberNavController())
}