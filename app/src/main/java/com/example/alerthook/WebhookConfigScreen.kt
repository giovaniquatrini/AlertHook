
package com.example.alerthook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alerthook.ui.theme.AlertHookTheme

@Composable
fun WebhookConfigScreen(navController: NavController) {
    AlertHookTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Configuração do Webhook",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Text("Campos de configuração do webhook aqui (a ser implementado)")

                Button(onClick = { navController.popBackStack() }) {
                    Text("Voltar")
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
