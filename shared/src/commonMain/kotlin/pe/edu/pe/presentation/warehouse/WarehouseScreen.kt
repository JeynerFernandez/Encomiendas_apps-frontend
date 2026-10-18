package pe.edu.pe.presentation.warehouse

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.data.model.Shipment
import pe.edu.pe.data.model.ShipmentStatus
import pe.edu.pe.data.repository.ShipmentRepository
import pe.edu.pe.presentation.theme.Border
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.LightBackground
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.Navy
import pe.edu.pe.presentation.theme.NavySoft
import pe.edu.pe.presentation.theme.Orange
import pe.edu.pe.presentation.theme.Surface

@Composable
fun WarehouseScreen(
    onBackClick: () -> Unit
) {

    val shipments = ShipmentRepository.shipments

    val encomiendasAlmacen = shipments.filter {
        it.status == ShipmentStatus.ALMACEN
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // CABECERA
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Navy
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column {

                Text(
                    text = "Almacén",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                Text(
                    text = "Gestiona las encomiendas y asígnalas a una movilidad",
                    fontSize = 13.sp,
                    color = Muted
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Encomiendas en almacén (${encomiendasAlmacen.size})",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (encomiendasAlmacen.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = Border
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Surface
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = null,
                        tint = Muted,
                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "No hay encomiendas disponibles en almacén.",
                        color = Muted,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = encomiendasAlmacen,
                    key = { it.trackingCode }
                ) { shipment ->

                    WarehouseShipmentCard(
                        shipment = shipment
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun WarehouseShipmentCard(
    shipment: Shipment
) {

    var mostrarMovilidades by remember(
        shipment.trackingCode
    ) {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Border
        ),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = NavySoft,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = "Encomienda",
                        tint = Navy
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = shipment.trackingCode,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink
                    )

                    Text(
                        text = "${shipment.originBranch} → ${shipment.destinationBranch}",
                        fontSize = 13.sp,
                        color = Muted
                    )
                }

                Text(
                    text = "En almacén",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            HorizontalDivider(
                color = Border
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Destino: ${shipment.destinationBranch}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Peso: ${shipment.weight} kg",
                fontSize = 13.sp,
                color = Muted
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Remitente: ${shipment.senderDni}",
                fontSize = 13.sp,
                color = Muted
            )

            Text(
                text = "Destinatario: ${shipment.recipientDni}",
                fontSize = 13.sp,
                color = Muted
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (!mostrarMovilidades) {

                Button(
                    onClick = {
                        mostrarMovilidades = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Navy
                    )
                ) {

                    Icon(
                        imageVector = Icons.Outlined.LocalShipping,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Asignar movilidad",
                        fontWeight = FontWeight.Bold
                    )
                }

            } else {

                SelectorMovilidad(
                    shipment = shipment,
                    onCancelar = {
                        mostrarMovilidades = false
                    }
                )
            }
        }
    }
}


@Composable
private fun SelectorMovilidad(
    shipment: Shipment,
    onCancelar: () -> Unit
) {

    var placaSeleccionada by remember {
        mutableStateOf<String?>(null)
    }

    /*
     * DATOS SIMULADOS
     *
     * Más adelante estos vendrán
     * de Node.js + MySQL.
     */
    val movilidades = listOf(

        MovilidadSimulada(
            placa = "ABC-731",
            conductor = "Carlos Pérez",
            ruta = "Tarapoto → Moyobamba → Rioja → Nueva Cajamarca → Bagua Grande"
        ),

        MovilidadSimulada(
            placa = "M3A-245",
            conductor = "Luis Ramírez",
            ruta = "Tarapoto → Moyobamba → Rioja"
        )
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = "Selecciona la movilidad",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )

        /*
         * Por ahora mostramos todas las movilidades.
         *
         * Más adelante podremos filtrar:
         * solo las movilidades cuya ruta
         * pase por el destino de la encomienda.
         */
        movilidades.forEach { movilidad ->

            val seleccionada =
                placaSeleccionada == movilidad.placa

            Card(
                onClick = {
                    placaSeleccionada =
                        movilidad.placa
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color =
                        if (seleccionada) {
                            Navy
                        } else {
                            Border
                        }
                ),
                colors = CardDefaults.cardColors(
                    containerColor =
                        if (seleccionada) {
                            NavySoft
                        } else {
                            Surface
                        }
                )
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    Text(
                        text = "Unidad ${movilidad.placa}",
                        fontWeight = FontWeight.Bold,
                        color = Ink,
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "Conductor: ${movilidad.conductor}",
                        fontSize = 13.sp,
                        color = Muted
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = movilidad.ruta,
                        fontSize = 12.sp,
                        color = Muted
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Button(
            onClick = {

                val placa =
                    placaSeleccionada
                        ?: return@Button

                val movilidad =
                    movilidades.first {
                        it.placa == placa
                    }

                /*
                 * 1. Guardamos vehículo
                 *    y conductor.
                 */
                ShipmentRepository.asignarMovilidad(
                    trackingCode =
                        shipment.trackingCode,
                    placa =
                        movilidad.placa,
                    conductor =
                        movilidad.conductor
                )

                /*
                 * 2. Cambiamos el estado
                 *    ALMACEN → TRANSITO.
                 *
                 * Al hacer esto desaparece
                 * automáticamente del almacén
                 * y pasa a la movilidad.
                 */
                ShipmentRepository.despachar(
                    trackingCode =
                        shipment.trackingCode
                )

            },
            enabled =
                placaSeleccionada != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            )
        ) {

            Text(
                text = "Cargar en movilidad",
                fontWeight = FontWeight.Bold,
                color = Surface
            )
        }

        TextButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Cancelar",
                color = Muted
            )
        }
    }
}


private data class MovilidadSimulada(
    val placa: String,
    val conductor: String,
    val ruta: String
)