package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.RastreoPedido


interface TrackingRepository {

    suspend fun rastrear(
        codigoRastreo: String,
        clave: String
    ): Result<RastreoPedido>
}