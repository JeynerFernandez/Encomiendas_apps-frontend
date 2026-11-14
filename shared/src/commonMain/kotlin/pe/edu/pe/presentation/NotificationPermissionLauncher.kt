package pe.edu.pe.presentation

import androidx.compose.runtime.Composable

interface NotificationPermissionLauncher {
    fun request()
}

@Composable
expect fun rememberNotificationPermissionLauncher(
    onResult: (granted: Boolean) -> Unit
): NotificationPermissionLauncher