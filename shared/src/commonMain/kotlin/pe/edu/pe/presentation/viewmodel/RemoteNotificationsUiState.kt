package pe.edu.pe.presentation.viewmodel

import pe.edu.pe.domain.model.RemoteNotification

data class RemoteNotificationsUiState(
    val notifications: List<RemoteNotification> = emptyList(),
    val unreadCount: Int = 0,
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val error: String? = null
) {
    val isEmpty: Boolean
        get() = !isLoading && notifications.isEmpty()
}
