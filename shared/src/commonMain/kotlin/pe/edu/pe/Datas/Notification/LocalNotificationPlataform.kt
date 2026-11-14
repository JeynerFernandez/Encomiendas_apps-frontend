package pe.edu.pe.Datas.Notification

interface LocalNotificationPlataform{
    suspend fun showTestNotification(): Result<Unit>
}
object UnsupportedLocalNotificationPlataform: LocalNotificationPlataform {
    override suspend fun showTestNotification(): Result<Unit> =
        Result.failure(
            UnsupportedOperationException("Las notificaciones locales estas disponibles solo en andrios")
        )
}