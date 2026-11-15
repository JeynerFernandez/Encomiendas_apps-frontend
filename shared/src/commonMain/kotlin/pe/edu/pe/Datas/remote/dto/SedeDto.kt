package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CreateSedeRequest(
    val name: String,
    val city: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
@Serializable
data class UpdateSedeRequest(
    val name: String,
    val city: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val active: Boolean
)

@Serializable
data class SedeDto(
    val id: Int,
    val name: String,
    val city: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val active: Boolean,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String
)