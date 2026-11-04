package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.ListaTrasladosMovilidad


interface MovilidadRepository {

    suspend fun getMisTraslados():
            Result<ListaTrasladosMovilidad>


    suspend fun iniciarTraslado(
        trasladoId: Int
    ): Result<Unit>


    suspend fun finalizarTraslado(
        trasladoId: Int
    ): Result<Unit>


    suspend fun retornarASedeCentral(
        trasladoId: Int
    ): Result<Unit>


    suspend fun descargarPorSede(
        trasladoId: Int,
        sedeId: Int
    ): Result<Unit>


    suspend fun actualizarUbicacion(
        trasladoId: Int,
        latitud: Double,
        longitud: Double
    ): Result<Unit>
}