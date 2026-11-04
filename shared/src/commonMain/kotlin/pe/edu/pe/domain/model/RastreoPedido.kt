package pe.edu.pe.domain.model


data class RastreoPedido(
    val encomienda: EncomiendaRastreo =
        EncomiendaRastreo(),

    val progreso: List<ProgresoRastreo> =
        emptyList(),

    val historial: List<HistorialRastreo> =
        emptyList(),

    val movilidad: MovilidadRastreo =
        MovilidadRastreo()
)


data class EncomiendaRastreo(
    val id: Int = 0,
    val codigoRastreo: String = "",
    val estado: String = "",
    val mensajeEstado: String = "",
    val origen: SedeRastreo = SedeRastreo(),
    val destino: SedeRastreo = SedeRastreo(),
    val sedeActual: SedeRastreo = SedeRastreo(),
    val pesoKg: Double = 0.0,
    val createdAt: String = "",
    val updatedAt: String = "",
    val entregadoAt: String = ""
)


data class SedeRastreo(
    val id: Int = 0,
    val name: String = ""
)


data class ProgresoRastreo(
    val estado: String = "",
    val completado: Boolean = false,
    val actual: Boolean = false
)


data class HistorialRastreo(
    val id: Int = 0,
    val estado: String = "",
    val sede: SedeRastreo = SedeRastreo(),
    val mensaje: String = "",
    val fecha: String = ""
)


data class MovilidadRastreo(
    val disponible: Boolean = false,
    val enCurso: Boolean = false,
    val conductor: String = "",
    val vehiculo: String = "",
    val placa: String = "",
    val ruta: String = "",
    val tieneUbicacion: Boolean = false,
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val updatedAt: String = ""
)