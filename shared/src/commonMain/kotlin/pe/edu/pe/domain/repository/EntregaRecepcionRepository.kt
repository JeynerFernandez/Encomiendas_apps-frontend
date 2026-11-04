package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.ListaPendientesEntrega


interface EntregaRecepcionRepository {

    suspend fun getPendientes():
            Result<ListaPendientesEntrega>


    suspend fun entregar(
        encomiendaId: Int,
        dni: String,
        clave: String
    ): Result<String>
}