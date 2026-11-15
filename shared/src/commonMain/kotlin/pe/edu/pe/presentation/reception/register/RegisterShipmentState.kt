package pe.edu.pe.presentation.reception.register


data class RegisterShipmentState(

    // Remitente
    val senderDni: String = "",
    val senderId: Int? = null,
    val senderNames: String = "",
    val senderLastNames: String = "",

    // Destinatario
    val recipientDni: String = "",
    val recipientId: Int? = null,
    val recipientNames: String = "",
    val recipientLastNames: String = "",

    // Pago
    val paymentMethod: String = "",

    // Encomienda
    val weight: String = "",
    val length: String = "",
    val width: String = "",
    val height: String = "",
    val observation: String = "",

    // Sedes
    val originBranchId: Int? = null,
    val originBranch: String = "",

    val destinationBranchId: Int? = null,
    val destinationBranch: String = "",

    // Cotización
    val calculatedCost: Double? = null,
    val tarifaBase: Double? = null,
    val precioKgExcedente: Double? = null,

    // Estados
    val isSearchingSender: Boolean = false,
    val isSearchingRecipient: Boolean = false,
    val isLoading: Boolean = false,

    val errorMessage: String? = null
)