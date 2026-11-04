package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.Tarifa

interface TarifaRepository {

    suspend fun getTarifas():
            Result<List<Tarifa>>


    suspend fun saveTarifa(
        id: Int? = null,
        sedeOrigenId: Int,
        sedeDestinoId: Int,
        precioBase: Double,
        precioKgExcedente: Double,
        active: Boolean
    ): Result<Tarifa>
}