package pe.edu.pe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val sedeId: Int? = null,
    val token: String? = null,
)