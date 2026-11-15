package pe.edu.pe.domain.repository

import pe.edu.pe.domain.model.IngresoAlmacen
import pe.edu.pe.domain.model.ListaEncomiendasAlmacen
import pe.edu.pe.domain.model.ListaTrasladosDisponiblesAlmacen


interface AlmacenRepository {

    suspend fun getEncomiendasPorDestino(
        destinoId: Int,
        filtro: String
    ): Result<ListaEncomiendasAlmacen>

    suspend fun ingresarAlmacen(
        codigoRastreo: String
    ): Result<IngresoAlmacen>

    suspend fun getTrasladosDisponibles(
        destinoId: Int
    ): Result<ListaTrasladosDisponiblesAlmacen>
    suspend fun asignarEncomiendaATraslado(
        trasladoId: Int,
        encomiendaId: Int
    ): Result<Unit>
}