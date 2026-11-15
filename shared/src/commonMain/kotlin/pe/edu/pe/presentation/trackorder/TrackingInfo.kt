
package pe.edu.pe.presentation.trackorder


data class TrackingInfo(
    val codigo: String,
    val origen: String,
    val destino: String,
    val estado: String,
    val fechaEstimada: String,
    val conductor: String,
    val vehiculo: String,
    val placa: String
)