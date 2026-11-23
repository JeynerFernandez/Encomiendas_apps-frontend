package pe.edu.pe.domain.model

import kotlinx.serialization.json.JsonObject

data class RemoteNotification(
    val id: Int,
    val type: String,
    val title: String,
    val message: String,
    val isRead: Boolean,
    val readAt: String?,
    val createdAt: String,
    val metadata: JsonObject?,
    val encomienda: RemoteNotificationEncomienda?
)

data class RemoteNotificationEncomienda(
    val id: Int,
    val codigoRastreo: String,
    val estado: String
)

data class MarkedNotificationRead(
    val id: Int,
    val isRead: Boolean,
    val readAt: String?
)

data class MarkedAllNotificationsRead(
    val updated: Int
)
