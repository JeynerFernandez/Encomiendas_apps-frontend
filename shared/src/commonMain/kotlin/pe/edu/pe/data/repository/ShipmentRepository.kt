package pe.edu.pe.data.repository

import androidx.compose.runtime.mutableStateListOf
import pe.edu.pe.data.model.Shipment
import pe.edu.pe.data.model.ShipmentStatus

object ShipmentRepository {

    // Lista única compartida por toda la aplicación
    val shipments = mutableStateListOf<Shipment>()

    // Registrar nueva encomienda
    fun agregarShipment(
        shipment: Shipment
    ) {
        shipments.add(shipment)
    }

    // Buscar por código
    fun buscarPorCodigo(
        codigo: String
    ): Shipment? {

        return shipments.firstOrNull {
            it.trackingCode.equals(
                codigo,
                ignoreCase = true
            )
        }
    }

    // Encomiendas recién registradas
    fun obtenerRegistrados(): List<Shipment> {

        return shipments.filter {
            it.status == ShipmentStatus.REGISTRADO
        }
    }

    // Encomiendas esperando ingresar al almacén
    fun obtenerEsperandoAlmacen(): List<Shipment> {

        return shipments.filter {
            it.status == ShipmentStatus.ESPERANDO_ALMACEN
        }
    }

    // Encomiendas que ya están físicamente en almacén
    fun obtenerEnAlmacen(): List<Shipment> {

        return shipments.filter {
            it.status == ShipmentStatus.ALMACEN
        }
    }

    // Encomiendas cargadas a una determinada movilidad
    fun obtenerPorMovilidad(
        placa: String
    ): List<Shipment> {

        return shipments.filter {
            it.vehiclePlate.equals(
                placa,
                ignoreCase = true
            ) &&
                    it.status == ShipmentStatus.TRANSITO
        }
    }

    // Recepción entrega la encomienda para almacén
    fun enviarAAlmacen(
        trackingCode: String
    ) {

        modificarShipment(trackingCode) {

            it.copy(
                status = ShipmentStatus.ESPERANDO_ALMACEN
            )
        }
    }

    // Almacén confirma recepción
    fun recibirEnAlmacen(
        trackingCode: String
    ) {

        modificarShipment(trackingCode) {

            it.copy(
                status = ShipmentStatus.ALMACEN
            )
        }
    }

    // Asignar una movilidad
    fun asignarMovilidad(
        trackingCode: String,
        placa: String,
        conductor: String
    ) {

        modificarShipment(trackingCode) {

            it.copy(
                vehiclePlate = placa,
                driver = conductor
            )
        }
    }

    // Sale de almacén e inicia tránsito
    fun despachar(
        trackingCode: String
    ) {

        modificarShipment(trackingCode) {

            it.copy(
                status = ShipmentStatus.TRANSITO
            )
        }
    }

    // Actualizar el Shipment completo
    // Sirve para las pantallas que ya trabajan devolviendo un Shipment modificado
    fun actualizarShipment(
        shipment: Shipment
    ) {

        val index = shipments.indexOfFirst {
            it.trackingCode.equals(
                shipment.trackingCode,
                ignoreCase = true
            )
        }

        if (index != -1) {
            shipments[index] = shipment
        }
    }

    private fun modificarShipment(
        trackingCode: String,
        transform: (Shipment) -> Shipment
    ) {

        val index = shipments.indexOfFirst {

            it.trackingCode.equals(
                trackingCode,
                ignoreCase = true
            )
        }

        if (index != -1) {

            shipments[index] =
                transform(shipments[index])
        }
    }
}