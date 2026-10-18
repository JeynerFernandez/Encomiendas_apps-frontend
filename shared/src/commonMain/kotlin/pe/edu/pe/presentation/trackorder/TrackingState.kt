package pe.edu.pe.presentation.trackorder

data class TrackingState(
    val codigo: String = "",
    val isLoading: Boolean = false,
    val pedidoEncontrado: Boolean = false,
    val trackingInfo: TrackingInfo? = null,
    val error: String? = null
)