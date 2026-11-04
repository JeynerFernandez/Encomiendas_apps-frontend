package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.repository.SedeRepository

class GetSedesUseCase(private val repository: SedeRepository) {
    suspend operator fun invoke(): Result<List<Sede>>{
        return repository.getSedes()
    }

}