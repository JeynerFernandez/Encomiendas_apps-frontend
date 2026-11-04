package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TrackingRequestDto(
    val codigoRastreo: String,
    val clave: String
)


@Serializable
data class TrackingResponseDto(
    val encomienda: EncomiendaRastreoDto,
    val progreso: List<ProgresoRastreoDto>,
    val historial: List<HistorialRastreoDto>,
    val movilidad: MovilidadRastreoDto
)


@Serializable
data class EncomiendaRastreoDto(
    val id: Int,

    @SerialName("codigo_rastreo")
    val codigoRastreo: String,

    val estado: String,

    @SerialName("mensaje_estado")
    val mensajeEstado: String,

    val origen: SedeRastreoDto,
    val destino: SedeRastreoDto,

    @SerialName("sede_actual")
    val sedeActual: SedeRastreoDto,

    @SerialName("peso_kg")
    val pesoKg: Double,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("entregado_at")
    val entregadoAt: String? = null
)


@Serializable
data class SedeRastreoDto(
    val id: Int,
    val name: String
)


@Serializable
data class ProgresoRastreoDto(
    val estado: String,
    val completado: Boolean,
    val actual: Boolean
)


@Serializable
data class HistorialRastreoDto(
    val id: Int,
    val estado: String,
    val sede: SedeRastreoDto? = null,
    val mensaje: String,
    val fecha: String
)


@Serializable
data class MovilidadRastreoDto(
    val disponible: Boolean,

    @SerialName("en_curso")
    val enCurso: Boolean,

    val conductor: String,
    val vehiculo: String,
    val placa: String,
    val ruta: String,

    @SerialName("tiene_ubicacion")
    val tieneUbicacion: Boolean,

    val latitud: Double,
    val longitud: Double,

    @SerialName("updated_at")
    val updatedAt: String? = null
)