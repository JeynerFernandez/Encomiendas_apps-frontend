package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.RemoteNotification
import pe.edu.pe.domain.repository.RemoteNotificationsRepository

class GetRemoteNotificationsUseCase(
    private val repository: RemoteNotificationsRepository
) {
    suspend operator fun invoke(): Result<List<RemoteNotification>> =
        repository.getNotifications()
}
