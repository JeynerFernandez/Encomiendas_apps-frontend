package pe.edu.pe.domain.model

data class RutaSede(
    val id: Int,
    val sedeId: Int,
    val sedeNombre: String,
    val orden: Int
)

data class Ruta(
    val id: Int,
    val name: String,
    val active: Boolean,
    val sedes: List<RutaSede>
)