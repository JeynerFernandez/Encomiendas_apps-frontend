package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.Tarifa
import pe.edu.pe.domain.repository.TarifaRepository

class SaveTarifaUseCase(
    private val repository: TarifaRepository
) {

    suspend operator fun invoke(
        id: Int? = null,
        sedeOrigenId: Int,
        sedeDestinoId: Int,
        precioBase: Double,
        precioKgExcedente: Double,
        active: Boolean
    ): Result<Tarifa> {

        return repository.saveTarifa(

            id =
                id,

            sedeOrigenId =
                sedeOrigenId,

            sedeDestinoId =
                sedeDestinoId,

            precioBase =
                precioBase,

            precioKgExcedente =
                precioKgExcedente,

            active =
                active
        )
    }
}