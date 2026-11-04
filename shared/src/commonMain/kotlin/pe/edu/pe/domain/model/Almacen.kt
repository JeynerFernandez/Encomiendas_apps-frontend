package pe.edu.pe.domain.model

data class SedeAlmacen(
    val id: Int,
    val name: String
)

data class TrasladoAsignadoAlmacen(
    val id: Int,
    val estado: String,
    val vehiculoId: Int,
    val placa: String,
    val vehiculoDescripcion: String,
    val conductorId: Int,
    val conductorNombre: String,
    val rutaId: Int,
    val rutaNombre: String,
    val recorrido: List<String>
)

data class EncomiendaAlmacen(
    val id: Int,
    val codigoRastreo: String,
    val estado: String,
    val remitente: String,
    val destinatario: String,
    val sedeOrigenId: Int,
    val sedeOrigen: String,
    val sedeDestinoId: Int,
    val sedeDestino: String,
    val pesoKg: Double,
    val costoTotal: Double,
    val observacion: String?,
    val modalidadPago: String?,
    val estadoPago: String?,
    val trasladoAsignado: TrasladoAsignadoAlmacen?
)

data class ListaEncomiendasAlmacen(
    val filtro: String,
    val sedeOrigen: SedeAlmacen,
    val sedeDestino: SedeAlmacen,
    val total: Int,
    val encomiendas: List<EncomiendaAlmacen>
)

data class IngresoAlmacen(
    val message: String,
    val id: Int,
    val codigoRastreo: String,
    val estado: String,
    val sedeActual: SedeAlmacen
)

data class VehiculoTrasladoAlmacen(
    val id: Int,
    val placa: String,
    val descripcion: String,
    val capacidadKg: Double,
    val cargaActualKg: Double,
    val capacidadDisponibleKg: Double
)

data class ConductorTrasladoAlmacen(
    val id: Int,
    val name: String
)

data class TrasladoDisponibleAlmacen(
    val trasladoId: Int,
    val estado: String,
    val vehiculo: VehiculoTrasladoAlmacen,
    val conductor: ConductorTrasladoAlmacen,
    val recorrido: List<String>
)

data class ListaTrasladosDisponiblesAlmacen(
    val sedeOrigenId: Int,
    val sedeDestinoId: Int,
    val total: Int,
    val traslados: List<TrasladoDisponibleAlmacen>
)