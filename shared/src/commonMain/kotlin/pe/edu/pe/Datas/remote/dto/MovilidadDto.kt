package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ListaTrasladosMovilidadDto(

    val conductor:
    ConductorMovilidadDto,

    val resumen:
    ResumenGeneralMovilidadDto,

    val traslados:
    List<TrasladoMovilidadDto>
)


@Serializable
data class ConductorMovilidadDto(

    val id:
    Int,

    val name:
    String
)


@Serializable
data class ResumenGeneralMovilidadDto(

    val total:
    Int,

    val programados:
    Int,

    @SerialName(
        "en_curso"
    )
    val enCurso:
    Int,

    val finalizados:
    Int,

    val cancelados:
    Int
)


@Serializable
data class TrasladoMovilidadDto(

    val id:
    Int,

    val estado:
    String,

    @SerialName(
        "salida_at"
    )
    val salidaAt:
    String? = null,

    @SerialName(
        "llegada_at"
    )
    val llegadaAt:
    String? = null,

    @SerialName(
        "created_at"
    )
    val createdAt:
    String? = null,

    @SerialName(
        "updated_at"
    )
    val updatedAt:
    String? = null,

    val ruta:
    RutaMovilidadDto,

    val vehiculo:
    VehiculoMovilidadDto,

    val conductor:
    ConductorMovilidadDto,

    val resumen:
    ResumenTrasladoMovilidadDto,

    val encomiendas:
    List<DetalleEncomiendaMovilidadDto>
)


@Serializable
data class RutaMovilidadDto(

    val id:
    Int,

    val name:
    String,

    val recorrido:
    List<SedeRutaMovilidadDto>
)


@Serializable
data class SedeRutaMovilidadDto(

    val orden:
    Int,

    val sede:
    SedeMovilidadDto
)


@Serializable
data class SedeMovilidadDto(

    val id:
    Int,

    val name:
    String
)


@Serializable
data class VehiculoMovilidadDto(

    val id:
    Int,

    val placa:
    String,

    val descripcion:
    String,

    @SerialName(
        "capacidad_kg"
    )
    val capacidadKg:
    Double
)


@Serializable
data class ResumenTrasladoMovilidadDto(

    @SerialName(
        "total_encomiendas"
    )
    val totalEncomiendas:
    Int,

    @SerialName(
        "pendientes_descarga"
    )
    val pendientesDescarga:
    Int,

    val descargadas:
    Int
)


@Serializable
data class DetalleEncomiendaMovilidadDto(

    val detalleId:
    Int,

    @SerialName(
        "descargado_at"
    )
    val descargadoAt:
    String? = null,

    val encomienda:
    EncomiendaMovilidadDto
)


@Serializable
data class EncomiendaMovilidadDto(

    val id:
    Int,

    @SerialName(
        "codigo_rastreo"
    )
    val codigoRastreo:
    String,

    val observacion:
    String = "",

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

    @SerialName(
        "peso_kg"
    )
    val pesoKg:
    Double,

    val estado:
    String,

    val destinatario:
    DestinatarioMovilidadDto,

    @SerialName(
        "sede_destino"
    )
    val sedeDestino:
    SedeMovilidadDto,

    @SerialName(
        "ultima_sede"
    )
    val ultimaSede:
    SedeMovilidadDto
)


@Serializable
data class DestinatarioMovilidadDto(

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
data class UpdateUbicacionMovilidadRequestDto(

    val latitud:
    Double,

    val longitud:
    Double
)