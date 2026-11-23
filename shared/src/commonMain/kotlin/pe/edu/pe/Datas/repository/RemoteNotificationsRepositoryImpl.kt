package pe.edu.pe.Datas.repository

import pe.edu.pe.Datas.remote.api.NotificationsApi
import pe.edu.pe.Datas.remote.mapper.toDomain
import pe.edu.pe.domain.model.MarkedAllNotificationsRead
import pe.edu.pe.domain.model.MarkedNotificationRead
import pe.edu.pe.domain.model.RemoteNotification
import pe.edu.pe.domain.repository.RemoteNotificationsRepository

class RemoteNotificationsRepositoryImpl(
    private val notificationsApi: NotificationsApi
) : RemoteNotificationsRepository {

    override suspend fun getNotifications(): Result<List<RemoteNotification>> {
        return notificationsApi
            .getNotifications()
            .map { notifications ->
                notifications.map { it.toDomain() }
            }
    }

    override suspend fun getUnreadCount(): Result<Int> {
        return notificationsApi
            .getUnreadCount()
            .map { it.toDomain() }
    }

    override suspend fun markAsRead(
        notificationId: Int
    ): Result<MarkedNotificationRead> {
        return notificationsApi
            .markAsRead(notificationId)
            .map { it.toDomain() }
    }

    override suspend fun markAllAsRead(): Result<MarkedAllNotificationsRead> {
        return notificationsApi
            .markAllAsRead()
            .map { it.toDomain() }
    }
}
