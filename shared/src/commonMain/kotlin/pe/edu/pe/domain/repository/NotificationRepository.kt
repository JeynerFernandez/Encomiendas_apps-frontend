package pe.edu.pe.domain.repository

interface NotificationRepository {
    fun isEnabled(): Boolean
    fun setEnable(enabled: Boolean)
    suspend fun sendTestNotification(): Result<Unit>
}