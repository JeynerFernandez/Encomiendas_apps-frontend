package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.ListaPendientesEntrega
import pe.edu.pe.domain.repository.EntregaRecepcionRepository


class EntregaRecepcionUseCase(
    private val repository:
    EntregaRecepcionRepository
) {


    suspend fun getPendientes():
            Result<ListaPendientesEntrega> {

        return repository
            .getPendientes()
    }


    suspend fun entregar(
        encomiendaId: Int,
        dni: String,
        clave: String
    ): Result<String> {

        if (
            encomiendaId <= 0
        ) {

            return Result.failure(
                Exception(
                    "Encomienda no válida"
                )
            )
        }


        if (
            dni.length != 8
        ) {

            return Result.failure(
                Exception(
                    "El DNI debe contener 8 dígitos"
                )
            )
        }


        if (
            clave.length != 4
        ) {

            return Result.failure(
                Exception(
                    "La clave debe contener 4 dígitos"
                )
            )
        }


        return repository
            .entregar(
                encomiendaId =
                    encomiendaId,

                dni =
                    dni,

                clave =
                    clave
            )
    }
}