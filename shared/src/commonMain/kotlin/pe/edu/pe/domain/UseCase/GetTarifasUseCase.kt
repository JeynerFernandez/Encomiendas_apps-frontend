package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.Tarifa
import pe.edu.pe.domain.repository.TarifaRepository

class GetTarifasUseCase(
    private val repository: TarifaRepository
) {

    suspend operator fun invoke():
            Result<List<Tarifa>> {

        return repository.getTarifas()
    }
}