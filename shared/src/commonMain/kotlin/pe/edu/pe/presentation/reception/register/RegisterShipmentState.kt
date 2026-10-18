package pe.edu.pe.presentation.reception.register

import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight

data class RegisterShipmentState(
    val senderDni: String = "",
    val recipientDni: String = "",
    val paymentMethod: String = "",
    val weight: String = "",
    val length: String = "",
    val width: String = "",
    val height: String = "",
    val originBranch: String = "",
    val destinationBranch: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)