package pe.edu.pe.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Tarifa(
    val id: Int,
    val sedeOrigenId: Int,
    val sedeDestinoId: Int,
    val sedeOrigen: String,
    val sedeDestino: String,
    val precioBase: Double,
    val precioKgExcedente: Double,
    val active: Boolean
)