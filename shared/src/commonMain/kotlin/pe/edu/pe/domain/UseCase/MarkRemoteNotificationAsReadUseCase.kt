package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.MarkedNotificationRead
import pe.edu.pe.domain.repository.RemoteNotificationsRepository

class MarkRemoteNotificationAsReadUseCase(
    private val repository: RemoteNotificationsRepository
) {
    suspend operator fun invoke(
        notificationId: Int
    ): Result<MarkedNotificationRead> =
        repository.markAsRead(notificationId)
}
