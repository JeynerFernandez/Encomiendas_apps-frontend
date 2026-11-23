package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RemoteNotificationDto(
    val id: Int,
    val type: String,
    val title: String,
    val message: String,
    val isRead: Boolean,
    val readAt: String? = null,
    val createdAt: String,
    val metadata: JsonObject? = null,
    val encomienda: RemoteNotificationEncomiendaDto? = null
)

@Serializable
data class RemoteNotificationEncomiendaDto(
    val id: Int,
    @SerialName("codigo_rastreo")
    val codigoRastreo: String,
    val estado: String
)

@Serializable
data class UnreadNotificationCountDto(
    val count: Int
)

@Serializable
data class MarkNotificationReadResponseDto(
    val id: Int,
    val isRead: Boolean,
    val readAt: String? = null
)

@Serializable
data class MarkAllNotificationsReadResponseDto(
    val updated: Int
)
