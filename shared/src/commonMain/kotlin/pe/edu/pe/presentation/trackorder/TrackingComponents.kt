package pe.edu.pe.presentation.trackorder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.pe.domain.model.ProgresoRastreo
import pe.edu.pe.domain.model.RastreoPedido
import pe.edu.pe.presentation.theme.Border
import pe.edu.pe.presentation.theme.Ink
import pe.edu.pe.presentation.theme.Muted
import pe.edu.pe.presentation.theme.ScaledText
import pe.edu.pe.presentation.theme.Surface


private val TrackingNavy =
    Color(
        0xFF0F2A46
    )


private val TrackingOrange =
    Color(
        0xFFF28C28
    )


private val TrackingGreen =
    Color(
        0xFF2E7D32
    )


private val TrackingRed =
    Color(
        0xFFC62828
    )


@Composable
fun PedidoEncontrado(
    rastreo: RastreoPedido,
    onActualizar: () -> Unit
) {

    val encomienda =
        rastreo.encomienda


    val movilidad =
        rastreo.movilidad


    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    top =
                        6.dp
                ),

        shape =
            RoundedCornerShape(
                16.dp
            ),

        border =
            BorderStroke(
                1.dp,
                Border
            ),

        colors =
            CardDefaults
                .cardColors(
                    containerColor =
                        Surface
                ),

        elevation =
            CardDefaults
                .cardElevation(
                    defaultElevation =
                        0.dp
                )
    ) {

        Column(
            modifier =
                Modifier.padding(
                    18.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )
        ) {



            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    ScaledText(
                        text =
                            "Pedido encontrado",

                        fontSize =
                            17f,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Ink
                    )


                    ScaledText(
                        text =
                            encomienda
                                .codigoRastreo,

                        fontSize =
                            12f,

                        color =
                            Muted
                    )
                }


                Box(
                    modifier =
                        Modifier
                            .background(
                                color =
                                    obtenerColorEstado(
                                        encomienda.estado
                                    )
                                        .copy(
                                            alpha =
                                                0.12f
                                        ),

                                shape =
                                    RoundedCornerShape(
                                        20.dp
                                    )
                            )
                            .padding(
                                horizontal =
                                    10.dp,

                                vertical =
                                    5.dp
                            )
                ) {

                    ScaledText(
                        text =
                            nombreEstado(
                                encomienda.estado
                            ),

                        fontSize =
                            10f,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            obtenerColorEstado(
                                encomienda.estado
                            )
                    )
                }
            }



            ScaledText(
                text =
                    encomienda
                        .mensajeEstado,

                fontSize =
                    13f,

                color =
                    Muted
            )


            HorizontalDivider(
                color =
                    Border
            )


            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    ScaledText(
                        text =
                            "Origen",

                        fontSize =
                            11f,

                        color =
                            Muted
                    )


                    ScaledText(
                        text =
                            encomienda
                                .origen
                                .name,

                        fontSize =
                            14f,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )
                }


                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        ),

                    horizontalAlignment =
                        Alignment.End
                ) {

                    ScaledText(
                        text =
                            "Destino",

                        fontSize =
                            11f,

                        color =
                            Muted
                    )


                    ScaledText(
                        text =
                            encomienda
                                .destino
                                .name,

                        fontSize =
                            14f,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )
                }
            }


            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    ScaledText(
                        text =
                            "Sede actual",

                        fontSize =
                            11f,

                        color =
                            Muted
                    )


                    ScaledText(
                        text =
                            encomienda
                                .sedeActual
                                .name,

                        fontSize =
                            13f,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )
                }


                Column(
                    horizontalAlignment =
                        Alignment.End
                ) {

                    ScaledText(
                        text =
                            "Peso",

                        fontSize =
                            11f,

                        color =
                            Muted
                    )


                    ScaledText(
                        text =
                            "${encomienda.pesoKg} kg",

                        fontSize =
                            13f,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Ink
                    )
                }
            }


            HorizontalDivider(
                color =
                    Border
            )


            ScaledText(
                text =
                    "Progreso del pedido",

                fontSize =
                    15f,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Ink
            )


            for (
            paso in
            rastreo.progreso
            ) {

                PasoProgreso(
                    progreso =
                        paso
                )
            }


            HorizontalDivider(
                color =
                    Border
            )


            ScaledText(
                text =
                    "Ubicación",

                fontSize =
                    15f,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Ink
            )


            TrackingMap(
                latitud =
                    movilidad.latitud,

                longitud =
                    movilidad.longitud,

                tieneUbicacion =
                    movilidad
                        .tieneUbicacion
            )


            if (
                encomienda.estado ==
                "EN_TRANSITO"
            ) {

                if (
                    movilidad
                        .tieneUbicacion
                ) {

                    ScaledText(
                        text =
                            "Última ubicación registrada por la movilidad.",

                        fontSize =
                            12f,

                        color =
                            Muted
                    )

                } else {

                    ScaledText(
                        text =
                            "La movilidad todavía no ha enviado una ubicación.",

                        fontSize =
                            12f,

                        color =
                            Muted
                    )
                }

            } else {

                ScaledText(
                    text =
                        "Ubicación operativa: ${encomienda.sedeActual.name}",

                    fontSize =
                        12f,

                    color =
                        Muted
                )
            }




            if (
                movilidad.disponible
            ) {

                HorizontalDivider(
                    color =
                        Border
                )


                ScaledText(
                    text =
                        "Información de transporte",

                    fontSize =
                        14f,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )


                if (
                    movilidad
                        .vehiculo
                        .isNotBlank()
                ) {

                    ScaledText(
                        text =
                            "Vehículo: ${movilidad.vehiculo}",

                        fontSize =
                            13f,

                        color =
                            Ink
                    )
                }


                if (
                    movilidad
                        .placa
                        .isNotBlank()
                ) {

                    ScaledText(
                        text =
                            "Placa: ${movilidad.placa}",

                        fontSize =
                            13f,

                        color =
                            Ink
                    )
                }


                if (
                    movilidad
                        .ruta
                        .isNotBlank()
                ) {

                    ScaledText(
                        text =
                            "Ruta: ${movilidad.ruta}",

                        fontSize =
                            13f,

                        color =
                            Ink
                    )
                }
            }


            if (
                rastreo
                    .historial
                    .isNotEmpty()
            ) {

                HorizontalDivider(
                    color =
                        Border
                )


                ScaledText(
                    text =
                        "Historial",

                    fontSize =
                        15f,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Ink
                )


                for (
                historial in
                rastreo.historial
                ) {

                    Column(
                        verticalArrangement =
                            Arrangement.spacedBy(
                                2.dp
                            )
                    ) {

                        ScaledText(
                            text =
                                historial
                                    .mensaje,

                            fontSize =
                                13f,

                            fontWeight =
                                FontWeight.Medium,

                            color =
                                Ink
                        )


                        ScaledText(
                            text =
                                historial
                                    .fecha,

                            fontSize =
                                11f,

                            color =
                                Muted
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(
                                4.dp
                            )
                    )
                }
            }


            Button(
                onClick =
                    onActualizar,

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        12.dp
                    ),

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                TrackingNavy,

                            contentColor =
                                Color.White
                        )
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Refresh,

                    contentDescription =
                        "Actualizar"
                )


                Spacer(
                    modifier =
                        Modifier.padding(
                            horizontal =
                                4.dp
                        )
                )


                ScaledText(
                    text =
                        "ACTUALIZAR ESTADO",

                    fontSize =
                        13f,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color.White
                )
            }
        }
    }
}


@Composable
private fun PasoProgreso(
    progreso: ProgresoRastreo
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        val colorIndicador =
            if (
                progreso.actual
            ) {

                TrackingOrange

            } else if (
                progreso.completado
            ) {

                TrackingNavy

            } else {

                Border
            }


        Box(
            modifier =
                Modifier
                    .background(
                        color =
                            colorIndicador,

                        shape =
                            CircleShape
                    )
                    .padding(
                        horizontal =
                            9.dp,

                        vertical =
                            5.dp
                    )
        ) {

            ScaledText(
                text =
                    if (
                        progreso.actual
                    ) {

                        "●"

                    } else if (
                        progreso.completado
                    ) {

                        "✓"

                    } else {

                        "○"
                    },

                fontSize =
                    11f,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color.White
            )
        }


        Spacer(
            modifier =
                Modifier.padding(
                    horizontal =
                        5.dp
                )
        )


        Column {

            ScaledText(
                text =
                    nombreEstado(
                        progreso.estado
                    ),

                fontSize =
                    13f,

                fontWeight =
                    if (
                        progreso.actual
                    ) {

                        FontWeight.Bold

                    } else {

                        FontWeight.Medium
                    },

                color =
                    if (
                        progreso.completado
                    ) {

                        Ink

                    } else {

                        Muted
                    }
            )


            if (
                progreso.actual
            ) {

                ScaledText(
                    text =
                        "Estado actual",

                    fontSize =
                        10f,

                    color =
                        TrackingOrange
                )
            }
        }
    }
}


private fun nombreEstado(
    estado: String
): String {

    return when (
        estado
    ) {

        "REGISTRADO" -> {

            "Registrado"
        }


        "CAMINO_ALMACEN" -> {

            "Camino al almacén"
        }


        "EN_ALMACEN" -> {

            "En almacén"
        }


        "EN_TRANSITO" -> {

            "En tránsito"
        }


        "EN_SEDE_DESTINO" -> {

            "En sede destino"
        }


        "ENTREGADO" -> {

            "Entregado"
        }


        "CANCELADO" -> {

            "Cancelado"
        }


        else -> {

            estado
        }
    }
}

private fun obtenerColorEstado(
    estado: String
): Color {

    return when (
        estado
    ) {

        "REGISTRADO" -> {

            TrackingNavy
        }


        "CAMINO_ALMACEN" -> {

            TrackingOrange
        }


        "EN_ALMACEN" -> {

            TrackingNavy
        }


        "EN_TRANSITO" -> {

            TrackingOrange
        }


        "EN_SEDE_DESTINO" -> {

            TrackingGreen
        }


        "ENTREGADO" -> {

            TrackingGreen
        }


        "CANCELADO" -> {

            TrackingRed
        }


        else -> {

            TrackingNavy
        }
    }
}