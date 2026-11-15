package pe.edu.pe.Datas.repository

import com.russhwolf.settings.Settings
import pe.edu.pe.Datas.Notification.LocalNotificationPlataform
import pe.edu.pe.domain.repository.NotificationRepository

class NotificationRepositoryImpl (
    private val settings: Settings,
    private val localNotificationPlataform: LocalNotificationPlataform
): NotificationRepository{
    override fun isEnabled(): Boolean = settings.getBoolean(KEY_NOTIFICATION_ENABLED, false)


    override fun setEnable(enabled: Boolean) {
        settings.putBoolean(KEY_NOTIFICATION_ENABLED, enabled)
    }

    override suspend fun sendTestNotification(): Result<Unit> {
        if (!isEnabled()){
            return Result.failure(IllegalStateException("ACTIVA LAS NOTIFICACIONES ANTES DE ENVIAR"))
        }
        return localNotificationPlataform.showTestNotification()
    }
    private companion object{
        const val KEY_NOTIFICATION_ENABLED = "notifications_enable"
    }
}