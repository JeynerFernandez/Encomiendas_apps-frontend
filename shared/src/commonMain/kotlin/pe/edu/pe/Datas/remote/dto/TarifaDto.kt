package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Request para crear una tarifa
@Serializable
data class CreateTarifaRequest(

    val sedeOrigenId: Int,

    val sedeDestinoId: Int,

    @SerialName("precio_base")
    val precioBase: Double,

    @SerialName("precio_kg_excedente")
    val precioKgExcedente: Double
)


// Request para actualizar una tarifa
@Serializable
data class UpdateTarifaRequest(

    val sedeOrigenId: Int,

    val sedeDestinoId: Int,

    @SerialName("precio_base")
    val precioBase: Double,

    @SerialName("precio_kg_excedente")
    val precioKgExcedente: Double,

    val active: Boolean
)


// Sede que viene dentro de una tarifa
@Serializable
data class TarifaSedeDto(

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


// Respuesta que devuelve /tarifas
@Serializable
data class TarifaDto(

    val id: Int,

    val origen: TarifaSedeDto,

    val destino: TarifaSedeDto,

    @SerialName("precio_base")
    val precioBase: Double,

    @SerialName("precio_kg_excedente")
    val precioKgExcedente: Double,

    // Por defecto true por si el backend no envía este campo
    val active: Boolean = true
)