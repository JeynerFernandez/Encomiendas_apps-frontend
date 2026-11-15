package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Datos enviados al crear una persona
@Serializable
data class CreatePersonaRequest(

    val dni: String,

    val nombres: String,

    @SerialName("apellido_paterno")
    val apellidoPaterno: String,

    @SerialName("apellido_materno")
    val apellidoMaterno: String
)


// Datos enviados al editar una persona
@Serializable
data class UpdatePersonaRequest(

    val dni: String,

    val nombres: String,

    @SerialName("apellido_paterno")
    val apellidoPaterno: String,

    @SerialName("apellido_materno")
    val apellidoMaterno: String
)


// Respuesta recibida desde NestJS
@Serializable
data class PersonaDto(

    val id: Int,

    val dni: String,

    val nombres: String,

    @SerialName("apellido_paterno")
    val apellidoPaterno: String,

    @SerialName("apellido_materno")
    val apellidoMaterno: String,

    val active: Boolean = true,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)