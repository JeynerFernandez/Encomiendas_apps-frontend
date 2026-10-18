package pe.edu.pe.presentation.reception

import pe.edu.pe.data.model.Shipment

data class ReceptionState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val shipments: List<Shipment> = emptyList()
)