package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.pe.presentation.theme.*


@Composable
fun PedidoEncontrado(
    trackingInfo: TrackingInfo
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // CABECERA
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {

                ScaledText(
                    text = "Pedido encontrado",
                    fontSize = 17f,
                    fontWeight = FontWeight.Bold,
                    color = Ink,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = Orange.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        )
                ) {

                    ScaledText(
                        text = trackingInfo.estado,
                        fontSize = 11f,
                        fontWeight = FontWeight.SemiBold,
                        color = Orange
                    )
                }
            }

            HorizontalDivider(
                color = Border
            )

            // ORIGEN Y DESTINO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    ScaledText(
                        text = "Origen",
                        fontSize = 11f,
                        color = Muted
                    )

                    ScaledText(
                        text = trackingInfo.origen,
                        fontSize = 14f,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                }

                Column {

                    ScaledText(
                        text = "Destino",
                        fontSize = 11f,
                        color = Muted
                    )

                    ScaledText(
                        text = trackingInfo.destino,
                        fontSize = 14f,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                }
            }

            HorizontalDivider(
                color = Border
            )

            // MAPA
            TrackingMap(
                origen = trackingInfo.origen,
                destino = trackingInfo.destino
            )

            HorizontalDivider(
                color = Border
            )

            // INFORMACIÓN DE LA MOVILIDAD
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                ScaledText(
                    text = "Información de la movilidad",
                    fontSize = 14f,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )

                ScaledText(
                    text = "Conductor: ${trackingInfo.conductor}",
                    fontSize = 13f,
                    color = Ink
                )

                ScaledText(
                    text = "Vehículo: ${trackingInfo.vehiculo}",
                    fontSize = 13f,
                    color = Ink
                )

                ScaledText(
                    text = "Placa: ${trackingInfo.placa}",
                    fontSize = 13f,
                    color = Ink
                )

                ScaledText(
                    text = "Ubicación actualizada recientemente",
                    fontSize = 12f,
                    color = Muted
                )
            }

            HorizontalDivider(
                color = Border
            )

            // ESTADO DEL PEDIDO
            EstadoPedido(
                estado = trackingInfo.estado
            )

            ScaledText(
                text = "Fecha estimada: ${trackingInfo.fechaEstimada}",
                fontSize = 13f,
                fontWeight = FontWeight.Bold,
                color = Navy
            )
        }
    }
}

@Composable
fun EstadoPedido(
    estado: String
) {

    Column {

        ScaledText(
            text = "Estado",
            fontSize = 11f,
            color = Muted
        )

        ScaledText(
            text = estado,
            fontSize = 15f,
            fontWeight = FontWeight.SemiBold,
            color = Ink
        )
    }
}