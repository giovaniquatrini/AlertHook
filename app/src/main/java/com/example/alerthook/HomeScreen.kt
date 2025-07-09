package com.example.alerthook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alerthook.ui.theme.AlertHookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    AlertHookTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AlertHook") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bem-vindo ao AlertHook!",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                ElevatedButton(
                    onClick = { navController.navigate("app_selection") },
                    modifier = Modifier.fillMaxSize(0.8f)
                ) {
                    Icon(Icons.Filled.Apps, contentDescription = "Selecionar Aplicativos")
                    Spacer(Modifier.height(8.dp))
                    Text("Selecionar Aplicativos")
                }

                Spacer(Modifier.height(16.dp))

                ElevatedButton(
                    onClick = { navController.navigate("webhook_config") },
                    modifier = Modifier.fillMaxSize(0.8f)
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configurar Webhook")
                    Spacer(Modifier.height(8.dp))
                    Text("Configurar Webhook")
                }

                Spacer(Modifier.height(32.dp))

                // Indicador de status (a ser implementado)
                Text(
                    text = "Status do Serviço: Desativado",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}