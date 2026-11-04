package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SedeAlmacenDto(
    val id: Int,
    val name: String
)

@Serializable
data class PersonaAlmacenDto(
    val id: Int? = null,
    val dni: String? = null,
    val nombres: String = "",
    @SerialName("apellido_paterno")
    val apellidoPaterno: String = "",
    @SerialName("apellido_materno")
    val apellidoMaterno: String = ""
)

@Serializable
data class PagoAlmacenDto(
    val id: Int? = null,
    val modalidad: String? = null,
    val estado: String? = null,
    val monto: JsonElement? = null
)

@Serializable
data class VehiculoAsignadoAlmacenDto(
    val id: Int,
    val placa: String,
    val descripcion: String
)

@Serializable
data class ConductorAsignadoAlmacenDto(
    val id: Int,
    val name: String
)

@Serializable
data class RutaAsignadaAlmacenDto(
    val id: Int,
    val name: String,
    val recorrido: List<String> = emptyList()
)

@Serializable
data class TrasladoAsignadoAlmacenDto(
    val id: Int,
    val estado: String,
    val vehiculo: VehiculoAsignadoAlmacenDto,
    val conductor: ConductorAsignadoAlmacenDto,
    val ruta: RutaAsignadaAlmacenDto
)

@Serializable
data class EncomiendaAlmacenDto(
    val id: Int,
    @SerialName("codigo_rastreo")
    val codigoRastreo: String,
    val estado: String,
    val remitente: PersonaAlmacenDto,
    val destinatario: PersonaAlmacenDto,
    @SerialName("sede_origen")
    val sedeOrigen: SedeAlmacenDto,
    @SerialName("sede_destino")
    val sedeDestino: SedeAlmacenDto,
    @SerialName("peso_kg")
    val pesoKg: JsonElement,
    @SerialName("costo_total")
    val costoTotal: JsonElement,
    val observacion: String? = null,
    val pago: PagoAlmacenDto? = null,
    @SerialName("traslado_asignado")
    val trasladoAsignado: TrasladoAsignadoAlmacenDto? = null
)

@Serializable
data class ListaEncomiendasAlmacenDto(
    val filtro: String,
    @SerialName("sede_origen")
    val sedeOrigen: SedeAlmacenDto,
    @SerialName("sede_destino")
    val sedeDestino: SedeAlmacenDto,
    val total: Int,
    val encomiendas: List<EncomiendaAlmacenDto>
)

@Serializable
data class EncomiendaIngresoAlmacenDto(
    val id: Int,
    @SerialName("codigo_rastreo")
    val codigoRastreo: String,
    val estado: String,
    @SerialName("sede_actual")
    val sedeActual: SedeAlmacenDto
)

@Serializable
data class IngresoAlmacenResponseDto(
    val message: String,
    val encomienda: EncomiendaIngresoAlmacenDto
)