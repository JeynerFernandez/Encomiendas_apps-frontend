package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.NotificationSettings
import pe.edu.pe.domain.repository.NotificationRepository

class GetNotificationSettingsUseCase(private val repository: NotificationRepository) {
    operator fun invoke()= NotificationSettings(isEnable = repository.isEnabled())
}