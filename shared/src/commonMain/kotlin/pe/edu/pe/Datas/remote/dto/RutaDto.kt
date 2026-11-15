package pe.edu.pe.Datas.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SedeRutaDto(
    val id: Int,
    val name: String
)

@Serializable
data class RutaSedeDto(
    val id: Int,
    val sede: SedeRutaDto,
    val orden: Int
)

@Serializable
data class RutaDto(
    val id: Int,
    val name: String,
    val active: Boolean,
    val sedes: List<RutaSedeDto>
)

@Serializable
data class RutaSedeRequestDto(
    val sedeId: Int,
    val orden: Int
)

@Serializable
data class CreateRutaRequestDto(
    val name: String,
    val sedes: List<RutaSedeRequestDto>
)

@Serializable
data class UpdateRutaRequestDto(
    val name: String,
    val active: Boolean,
    val sedes: List<RutaSedeRequestDto>
)