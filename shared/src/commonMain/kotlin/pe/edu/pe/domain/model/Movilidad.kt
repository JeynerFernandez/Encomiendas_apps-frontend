package pe.edu.pe.domain.model

data class ListaTrasladosMovilidad(
    val conductor: ConductorMovilidad,
    val resumen: ResumenGeneralMovilidad,
    val traslados: List<TrasladoMovilidad>
)


data class ConductorMovilidad(
    val id: Int,
    val name: String
)


data class ResumenGeneralMovilidad(
    val total: Int,
    val programados: Int,
    val enCurso: Int,
    val finalizados: Int,
    val cancelados: Int
)


data class TrasladoMovilidad(
    val id: Int,
    val estado: String,
    val salidaAt: String,
    val llegadaAt: String,
    val createdAt: String,
    val updatedAt: String,
    val ruta: RutaMovilidad,
    val vehiculo: VehiculoMovilidad,
    val conductor: ConductorMovilidad,
    val resumen: ResumenTrasladoMovilidad,
    val encomiendas: List<DetalleEncomiendaMovilidad>
)


data class RutaMovilidad(
    val id: Int,
    val name: String,
    val recorrido: List<SedeRutaMovilidad>
)


data class SedeRutaMovilidad(
    val orden: Int,
    val sede: SedeMovilidad
)


data class SedeMovilidad(
    val id: Int,
    val name: String
)


data class VehiculoMovilidad(
    val id: Int,
    val placa: String,
    val descripcion: String,
    val capacidadKg: Double
)


data class ResumenTrasladoMovilidad(
    val totalEncomiendas: Int,
    val pendientesDescarga: Int,
    val descargadas: Int
)


data class DetalleEncomiendaMovilidad(
    val detalleId: Int,
    val descargadoAt: String,
    val encomienda: EncomiendaMovilidad
)


data class EncomiendaMovilidad(
    val id: Int,
    val codigoRastreo: String,
    val observacion: String,
    val altoCm: Double,
    val anchoCm: Double,
    val largoCm: Double,
    val pesoKg: Double,
    val estado: String,
    val destinatario: DestinatarioMovilidad,
    val sedeDestino: SedeMovilidad,
    val ultimaSede: SedeMovilidad
)


data class DestinatarioMovilidad(
    val dni: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String
)