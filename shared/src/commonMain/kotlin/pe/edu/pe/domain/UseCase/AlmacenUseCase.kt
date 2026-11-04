package pe.edu.pe.domain.usecase

import pe.edu.pe.domain.model.IngresoAlmacen
import pe.edu.pe.domain.model.ListaEncomiendasAlmacen
import pe.edu.pe.domain.model.ListaTrasladosDisponiblesAlmacen
import pe.edu.pe.domain.repository.AlmacenRepository


class AlmacenUseCase(
    private val repository: AlmacenRepository
) {
    suspend fun getEncomiendas(
        destinoId: Int,
        filtro: String
    ): Result<ListaEncomiendasAlmacen> {

        return repository.getEncomiendasPorDestino(
            destinoId = destinoId,
            filtro = filtro
        )
    }

    suspend fun ingresarAlmacen(
        codigoRastreo: String
    ): Result<IngresoAlmacen> {

        return repository.ingresarAlmacen(
            codigoRastreo = codigoRastreo
        )
    }

    suspend fun getTrasladosDisponibles(
        destinoId: Int
    ): Result<ListaTrasladosDisponiblesAlmacen> {

        return repository.getTrasladosDisponibles(
            destinoId = destinoId
        )
    }
    suspend fun asignarEncomiendaATraslado(
        trasladoId: Int,
        encomiendaId: Int
    ): Result<Unit> {

        return repository.asignarEncomiendaATraslado(
            trasladoId = trasladoId,
            encomiendaId = encomiendaId
        )
    }
}