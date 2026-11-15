package pe.edu.pe.domain.repository
import pe.edu.pe.domain.model.Ruta

interface RutaRepository {

    suspend fun getRutas():
            Result<List<Ruta>>

    suspend fun crearRuta(
        name: String,
        sedeIds: List<Int>
    ): Result<Ruta>

    suspend fun actualizarRuta(
        id: Int,
        name: String,
        active: Boolean,
        sedeIds: List<Int>
    ): Result<Ruta>
}