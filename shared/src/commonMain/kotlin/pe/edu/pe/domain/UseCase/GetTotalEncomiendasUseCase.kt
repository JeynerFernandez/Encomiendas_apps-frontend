package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.repository.EncomiendaRepository

class GetTotalEncomiendasUseCase(
    private val repository: EncomiendaRepository
) {
    suspend operator fun invoke(): Result<Int> =
        repository.obtenerTotalEncomiendas()
}
