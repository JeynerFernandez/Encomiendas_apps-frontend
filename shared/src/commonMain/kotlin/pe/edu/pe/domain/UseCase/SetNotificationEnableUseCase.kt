package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.repository.NotificationRepository

class SetNotificationEnableUseCase(private val repository: NotificationRepository) {
    operator fun invoke(enabled: Boolean)=repository.setEnable(enabled)
}