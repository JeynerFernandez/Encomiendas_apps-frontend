package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.Sede

interface SedeRepository {
    suspend fun getSedes(): Result<List<Sede>>

    suspend fun saveSede(
        id: Int? = null,
        name: String,
        city: String,
        address: String,
        latitude: Double,
        longitude: Double,
        active: Boolean
    ): Result<Sede>
}