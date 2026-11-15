package pe.edu.pe.domain.model

data class RegistroEncomienda(

    val id: Int,

    val codigoRastreo: String,

    val claveRastreo: String,

    val costoTotal: Double,

    val estado: String,

    val remitenteDni: String,

    val remitenteNombres: String,

    val remitenteApellidos: String,

    val destinatarioDni: String,

    val destinatarioNombres: String,

    val destinatarioApellidos: String,

    val origen: String,

    val destino: String,

    val pagoModalidad: String?
)