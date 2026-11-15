package pe.edu.pe.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Persona(

    val id: Int,

    val dni: String,

    val nombres: String,

    val apellidoPaterno: String,

    val apellidoMaterno: String,

    val active: Boolean = true
) {

    val apellidos: String
        get() =
            "$apellidoPaterno $apellidoMaterno"
                .trim()


    val nombreCompleto: String
        get() =
            "$nombres $apellidoPaterno $apellidoMaterno"
                .trim()
}