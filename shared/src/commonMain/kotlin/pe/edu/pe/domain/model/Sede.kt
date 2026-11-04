package pe.edu.pe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Sede(
    val id: Int,
    val name: String,
    val city: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
    )