package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.*
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
@Serializable
data class RoleDto(
    val id: Int,
    val name: String,
    val active: Boolean
)
@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val active: Boolean,
    val sedeId: Int? = null,
    @SerialName("notificacion_token")
    val notificationToken: String?=null,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,
    val role: RoleDto

    )

@Serializable
data class AuthResponse(
    val user: UserDto,

    @SerialName("access_token")
    val accessToken: String
)
