package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ListaPendientesEntregaDto(

    val sede:
    SedePendienteDto,

    val total:
    Int,

    val encomiendas:
    List<EncomiendaPendienteEntregaDto>
)


@Serializable
data class SedePendienteDto(

    val id:
    Int
)


@Serializable
data class EncomiendaPendienteEntregaDto(

    val id:
    Int,

    @SerialName(
        "codigo_rastreo"
    )
    val codigoRastreo:
    String,

    val estado:
    String,

    @SerialName(
        "peso_kg"
    )
    val pesoKg:
    Double,

    @SerialName(
        "alto_cm"
    )
    val altoCm:
    Double,

    @SerialName(
        "ancho_cm"
    )
    val anchoCm:
    Double,

    @SerialName(
        "largo_cm"
    )
    val largoCm:
    Double,

    val observacion:
    String,

    val remitente:
    PersonaEntregaDto,

    val destinatario:
    PersonaEntregaDto,

    @SerialName(
        "sede_origen"
    )
    val sedeOrigen:
    SedeEntregaDto,

    @SerialName(
        "sede_destino"
    )
    val sedeDestino:
    SedeEntregaDto,

    @SerialName(
        "ultima_sede"
    )
    val ultimaSede:
    SedeEntregaDto
)


@Serializable
data class PersonaEntregaDto(

    val dni:
    String,

    val nombres:
    String,

    @SerialName(
        "apellido_paterno"
    )
    val apellidoPaterno:
    String,

    @SerialName(
        "apellido_materno"
    )
    val apellidoMaterno:
    String
)


@Serializable
data class SedeEntregaDto(

    val id:
    Int,

    val name:
    String
)


@Serializable
data class ConfirmarEntregaRequestDto(

    val dni:
    String,

    val clave:
    String
)


@Serializable
data class ConfirmarEntregaResponseDto(

    val message:
    String
)