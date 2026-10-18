package pe.edu.pe.data.model

data class Shipment(
    val trackingCode: String,
    val password: String,

    val senderDni: String,
    val recipientDni: String,

    val paymentMethod: String,

    val height: Double,
    val width: Double,
    val length: Double,
    val weight: Double,

    val originBranch: String,
    val destinationBranch: String,

    val billableWeight: Double,
    val cost: Double,

    val status: ShipmentStatus,

    val warehouseToken: String? = null,
    val vehiclePlate: String? = null,
    val driver: String? = null,

    val currentBranch: String? = null,
    val routeName: String? = null
)