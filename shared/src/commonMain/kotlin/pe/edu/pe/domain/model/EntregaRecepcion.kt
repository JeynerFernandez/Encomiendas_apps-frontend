package pe.edu.pe.domain.model


data class ListaPendientesEntrega(
    val sedeId: Int,
    val total: Int,
    val encomiendas: List<EncomiendaPendienteEntrega>
)


data class EncomiendaPendienteEntrega(
    val id: Int,
    val codigoRastreo: String,
    val estado: String,
    val pesoKg: Double,
    val altoCm: Double,
    val anchoCm: Double,
    val largoCm: Double,
    val observacion: String,
    val remitente: PersonaEntrega,
    val destinatario: PersonaEntrega,
    val sedeOrigen: SedeEntrega,
    val sedeDestino: SedeEntrega,
    val ultimaSede: SedeEntrega
)


data class PersonaEntrega(
    val dni: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String
)


data class SedeEntrega(
    val id: Int,
    val name: String
)