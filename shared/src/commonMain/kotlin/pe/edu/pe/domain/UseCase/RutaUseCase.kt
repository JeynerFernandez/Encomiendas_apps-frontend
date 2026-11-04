package pe.edu.pe.domain.UseCase
import pe.edu.pe.domain.model.Ruta
import pe.edu.pe.domain.repository.RutaRepository

class RutaUseCase(
    private val repository: RutaRepository
) {

    suspend fun getRutas():
            Result<List<Ruta>> {

        return repository
            .getRutas()
    }

    suspend fun crearRuta(
        name: String,
        sedeIds: List<Int>
    ): Result<Ruta> {

        if (
            name.isBlank()
        ) {

            return Result.failure(
                Exception(
                    "El nombre de la ruta es obligatorio"
                )
            )
        }

        if (
            sedeIds.size < 2
        ) {

            return Result.failure(
                Exception(
                    "La ruta debe tener mínimo dos sedes"
                )
            )
        }

        for (
        sedeId in sedeIds
        ) {

            if (
                sedeId <= 0
            ) {

                return Result.failure(
                    Exception(
                        "Existe una sede no válida"
                    )
                )
            }
        }

        val sedesUnicas =
            sedeIds.toSet()

        if (
            sedesUnicas.size !=
            sedeIds.size
        ) {

            return Result.failure(
                Exception(
                    "Una sede no puede repetirse dentro de la ruta"
                )
            )
        }

        return repository
            .crearRuta(
                name = name,
                sedeIds = sedeIds
            )
    }

    suspend fun actualizarRuta(
        id: Int,
        name: String,
        active: Boolean,
        sedeIds: List<Int>
    ): Result<Ruta> {

        if (
            id <= 0
        ) {

            return Result.failure(
                Exception(
                    "La ruta seleccionada no es válida"
                )
            )
        }

        if (
            name.isBlank()
        ) {

            return Result.failure(
                Exception(
                    "El nombre de la ruta es obligatorio"
                )
            )
        }

        if (
            sedeIds.size < 2
        ) {

            return Result.failure(
                Exception(
                    "La ruta debe tener mínimo dos sedes"
                )
            )
        }

        for (
        sedeId in sedeIds
        ) {

            if (
                sedeId <= 0
            ) {

                return Result.failure(
                    Exception(
                        "Existe una sede no válida"
                    )
                )
            }
        }

        val sedesUnicas =
            sedeIds.toSet()

        if (
            sedesUnicas.size !=
            sedeIds.size
        ) {

            return Result.failure(
                Exception(
                    "Una sede no puede repetirse dentro de la ruta"
                )
            )
        }

        return repository
            .actualizarRuta(
                id = id,
                name = name,
                active = active,
                sedeIds = sedeIds
            )
    }
}