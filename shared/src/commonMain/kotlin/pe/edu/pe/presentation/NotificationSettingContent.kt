package pe.edu.pe.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import pe.edu.pe.presentation.viewmodel.NotificationSettingsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsContent(
    viewModel: NotificationSettingsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val permissionLauncher = rememberNotificationPermissionLauncher(viewModel::onPermissionResult)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes de notificaciones") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text("Notificaciones", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Activa las notificaciones locales de esta app.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = state.isEnable,
                    onCheckedChange = { enabled ->
                        if (enabled) permissionLauncher.request()
                        else viewModel.setNotificationEnabled(false)
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = viewModel::sendNotification,
                enabled = state.isEnable && !state.isSendingTest,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isSendingTest) "Enviando…" else "Enviar notificación local de prueba")
            }

            state.feedbackMessage?.let { message ->
                Spacer(Modifier.height(16.dp))
                Text(
                    text = message,
                    color = if (state.isFeedBackError) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))
            Text(
                "Esta prueba se genera en el dispositivo; todavía no requiere Firebase ni el backend.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
