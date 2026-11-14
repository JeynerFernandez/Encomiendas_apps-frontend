package pe.edu.pe.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
actual fun rememberNotificationPermissionLauncher(
    onResult: (granted: Boolean) -> Unit
): NotificationPermissionLauncher {
    val currentOnResult by rememberUpdatedState(onResult)
    return remember {
        object : NotificationPermissionLauncher {
            override fun request() {
                currentOnResult(false)
            }
        }
    }
}