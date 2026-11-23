package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.repository.RemoteNotificationsRepository

class GetUnreadNotificationsCountUseCase(
    private val repository: RemoteNotificationsRepository
) {
    suspend operator fun invoke(): Result<Int> =
        repository.getUnreadCount()
}
