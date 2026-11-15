package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


// Request exacto que recibe POST /encomienda
@Serializable
data class CreateEncomiendaRequest(

    val remitenteId: Int,

    val destinatarioId: Int,

    val sedeOrigenId: Int,

    val sedeDestinoId: Int,

    @SerialName("alto_cm")
    val altoCm: Double,

    @SerialName("ancho_cm")
    val anchoCm: Double,

    @SerialName("largo_cm")
    val largoCm: Double,

    @SerialName("peso_kg")
    val pesoKg: Double,

    @SerialName("modalidad_pago")
    val modalidadPago: String,

    val observacion: String? = null
)


// Persona resumida que devuelve la encomienda
@Serializable
data class PersonaEncomiendaDto(

    val dni: String,

    val nombres: String,

    val apellidos: String
)


// Encomienda creada
@Serializable
data class EncomiendaCreadaDto(

    val id: Int,

    @SerialName("codigo_rastreo")
    val codigoRastreo: String,

    @SerialName("costo_total")
    val costoTotal: JsonElement,

    val estado: String,

    val remitente: PersonaEncomiendaDto,

    val destinatario: PersonaEncomiendaDto,

    val origen: String,

    val destino: String
)


// Pago creado junto con la encomienda
@Serializable
data class PagoEncomiendaDto(

    val id: Int,

    val modalidad: String,

    val estado: String,

    val monto: JsonElement
)


// Respuesta completa del POST
@Serializable
data class CreateEncomiendaResponseDto(

    val encomienda: EncomiendaCreadaDto,

    val pago: PagoEncomiendaDto? = null,

    @SerialName("clave_rastreo")
    val claveRastreo: String
)