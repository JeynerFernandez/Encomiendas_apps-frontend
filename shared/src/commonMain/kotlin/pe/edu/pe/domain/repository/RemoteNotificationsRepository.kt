package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.MarkedAllNotificationsRead
import pe.edu.pe.domain.model.MarkedNotificationRead
import pe.edu.pe.domain.model.RemoteNotification

interface RemoteNotificationsRepository {
    suspend fun getNotifications(): Result<List<RemoteNotification>>

    suspend fun getUnreadCount(): Result<Int>

    suspend fun markAsRead(
        notificationId: Int
    ): Result<MarkedNotificationRead>

    suspend fun markAllAsRead(): Result<MarkedAllNotificationsRead>
}
