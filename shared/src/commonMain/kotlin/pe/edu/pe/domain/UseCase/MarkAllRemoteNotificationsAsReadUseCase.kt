package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.MarkedAllNotificationsRead
import pe.edu.pe.domain.repository.RemoteNotificationsRepository

class MarkAllRemoteNotificationsAsReadUseCase(
    private val repository: RemoteNotificationsRepository
) {
    suspend operator fun invoke(): Result<MarkedAllNotificationsRead> =
        repository.markAllAsRead()
}
