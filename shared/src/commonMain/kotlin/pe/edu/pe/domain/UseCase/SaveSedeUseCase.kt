package pe.edu.pe.domain.usecase

import pe.edu.pe.domain.model.Sede
import pe.edu.pe.domain.repository.SedeRepository

class SaveSedeUseCase(
    private val repository: SedeRepository
) {

    suspend operator fun invoke(
        id: Int? = null,
        name: String,
        city: String,
        address: String,
        latitude: Double,
        longitude: Double,
        active: Boolean
    ): Result<Sede> {

        return repository.saveSede(
            id = id,
            name = name,
            city = city,
            address = address,
            latitude = latitude,
            longitude = longitude,
            active = active
        )
    }
}