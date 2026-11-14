package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.repository.NotificationRepository

class SendTestNotificationUseCase(private val repository: NotificationRepository)
{
    suspend operator fun invoke()=repository.sendTestNotification()
}