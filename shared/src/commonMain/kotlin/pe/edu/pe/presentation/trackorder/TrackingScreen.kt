package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.pe.presentation.theme.*

@Composable
fun TrackingScreen(
    onBackClick: () -> Unit
) {

    var state by remember {
        mutableStateOf(TrackingState())
    }

    // Para ejecutar la espera de 1 segundo
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // =========================
        // CABECERA
        // =========================

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Navy,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            ScaledText(
                text = "Rastrear pedido",
                fontSize = 25f,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        HorizontalDivider(
            color = Border
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // =========================
        // CONTENIDO CON SCROLL
        // =========================

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ScaledText(
                text = "Consulta el estado de tu pedido",
                fontSize = 18f,
                fontWeight = FontWeight.Bold,
                color = Ink
            )

            ScaledText(
                text = "Ingresa el código de seguimiento de tu pedido",
                fontSize = 13f,
                color = Muted
            )

            // =========================
            // CAMPO CÓDIGO
            // =========================

            OutlinedTextField(
                value = state.codigo,

                onValueChange = {

                    state = state.copy(
                        codigo = it,
                        error = null,
                        pedidoEncontrado = false,
                        trackingInfo = null,
                        isLoading = false
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    ScaledText(
                        text = "Código de seguimiento",
                        fontSize = 14f
                    )
                },

                placeholder = {
                    ScaledText(
                        text = "Ejemplo: MY-2026-001",
                        color = Muted,
                        fontSize = 14f
                    )
                },

                singleLine = true,

                shape = RoundedCornerShape(14.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Navy,
                    unfocusedBorderColor = Border,
                    focusedTextColor = Ink,
                    unfocusedTextColor = Ink
                )
            )

            // =========================
            // BOTÓN RASTREAR
            // =========================

            Button(
                onClick = {

                    coroutineScope.launch {

                        // Primero mostramos el loading
                        state = state.copy(
                            isLoading = true,
                            error = null,
                            pedidoEncontrado = false,
                            trackingInfo = null
                        )

                        // Simula consulta al servidor
                        delay(1000)

                        // Después buscamos el pedido
                        buscarPedido(
                            codigo = state.codigo,
                            onResult = { resultado ->
                                state = resultado
                            }
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                enabled =
                    state.codigo.isNotBlank() &&
                            !state.isLoading,

                shape = RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy,
                    contentColor = Color.White,
                    disabledContainerColor = NavySoft,
                    disabledContentColor = Navy
                )
            ) {

                if (state.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Navy,
                        strokeWidth = 2.dp
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    ScaledText(
                        text = "BUSCANDO...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14f
                    )

                } else {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    ScaledText(
                        text = "RASTREAR PEDIDO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15f
                    )
                }
            }

            // =========================
            // MENSAJE DE CARGA
            // =========================

            if (state.isLoading) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Navy,
                        strokeWidth = 2.dp
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    ScaledText(
                        text = "Buscando tu pedido...",
                        fontSize = 13f,
                        fontWeight = FontWeight.Medium,
                        color = Muted
                    )
                }
            }

            // =========================
            // ERROR
            // =========================

            state.error?.let { mensaje ->

                ScaledText(
                    text = mensaje,
                    color = Color(0xFFE53935),
                    fontSize = 13f,
                    fontWeight = FontWeight.Medium
                )
            }

            // =========================
            // PEDIDO ENCONTRADO
            // =========================

            if (
                state.pedidoEncontrado &&
                state.trackingInfo != null
            ) {

                PedidoEncontrado(
                    trackingInfo = state.trackingInfo!!
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}


// ======================================================
// BÚSQUEDA TEMPORAL DE PEDIDOS
// MÁS ADELANTE ESTO VENDRÁ DEL BACKEND NODE.JS
// ======================================================

private fun buscarPedido(
    codigo: String,
    onResult: (TrackingState) -> Unit
) {

    val codigoLimpio = codigo.trim()

    if (
        codigoLimpio.equals(
            "MY-2026-001",
            ignoreCase = true
        )
    ) {

        onResult(
            TrackingState(
                codigo = codigoLimpio,
                isLoading = false,
                pedidoEncontrado = true,

                trackingInfo = TrackingInfo(
                    codigo = codigoLimpio,
                    origen = "Tarapoto",
                    destino = "Lima",
                    estado = "En tránsito",
                    fechaEstimada = "10/09/2026",
                    conductor = "Carlos Pérez",
                    vehiculo = "Toyota Hiace",
                    placa = "M3A-245"
                )
            )
        )

    } else if (
        codigoLimpio.equals(
            "MY-2026-002",
            ignoreCase = true
        )
    ) {

        onResult(
            TrackingState(
                codigo = codigoLimpio,
                isLoading = false,
                pedidoEncontrado = true,

                trackingInfo = TrackingInfo(
                    codigo = codigoLimpio,
                    origen = "Lima",
                    destino = "Arequipa",
                    estado = "Preparando envío",
                    fechaEstimada = "12/09/2026",
                    conductor = "Luis Ramírez",
                    vehiculo = "Mercedes Sprinter",
                    placa = "B7P-821"
                )
            )
        )

    } else {

        onResult(
            TrackingState(
                codigo = codigoLimpio,
                isLoading = false,
                pedidoEncontrado = false,
                trackingInfo = null,
                error = "No se encontró ningún pedido con ese código."
            )
        )
    }
}