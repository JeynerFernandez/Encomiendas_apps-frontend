package pe.edu.pe.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.pe.presentation.theme.* // Importante para usar los colores personalizados


@Composable
fun ReportesAdminContent(
    onSalirClick: () -> Unit = {},
    onVerDetalleClick: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground) // Color de fondo claro (gris muy suave)

            // Evita que el contenido choque con la barra de hora, señal y batería
            .statusBarsPadding()

            .verticalScroll(rememberScrollState())

            // Margen lateral de toda la pantalla
            .padding(horizontal = 24.dp)
    ) {

        // Espacio entre la barra del celular y el encabezado
        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // Flecha para regresar al panel administrador
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onSalirClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Navy, // Color Azul Marino para el icono
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column {

                Text(
                    text = "Administrador",
                    fontSize = 13.sp,
                    color = Muted // Color gris para el subtítulo
                )

                Text(
                    text = "Reportes",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink // Color oscuro para el título principal
                )
            }
        }


        // Margen entre el encabezado y el contenido
        Spacer(
            modifier = Modifier.height(18.dp)
        )

        HorizontalDivider(
            color = Border // Color de borde sutil
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // Muestra un resumen general de las encomiendas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Encomiendas registradas
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    Border // Color de borde sutil
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Surface // Fondo blanco
                )
            ) {

                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Encomiendas registradas",
                        fontSize = 11.sp,
                        color = Muted, // Color gris para etiquetas
                        lineHeight = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "7",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el número
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "+8.2%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Navy // Color Azul Marino para el indicador
                    )
                }
            }


            // Encomiendas actualmente en tránsito
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    Border // Color de borde sutil
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Surface // Fondo blanco
                )
            ) {

                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "En tránsito",
                        fontSize = 11.sp,
                        color = Muted, // Color gris para etiquetas
                        lineHeight = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "2",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el número
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Activo",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Orange // Color Naranja para el indicador
                    )
                }
            }


            // Encomiendas entregadas
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    Border // Color de borde sutil
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Surface // Fondo blanco
                )
            ) {

                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Entregados",
                        fontSize = 11.sp,
                        color = Muted, // Color gris para etiquetas
                        lineHeight = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "1",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el número
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "96.8%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF279B57) // Color Verde para el indicador
                    )
                }
            }


            // Cantidad de incidencias registradas
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    Border // Color de borde sutil
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Surface // Fondo blanco
                )
            ) {

                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Incidencias",
                        fontSize = 11.sp,
                        color = Muted, // Color gris para etiquetas
                        lineHeight = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "2",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el número
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Ver",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Orange // Color Naranja para el indicador
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(30.dp)
        )


        // Muestra los envíos realizados durante los últimos meses
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                Border // Color de borde sutil
            ),
            colors = CardDefaults.cardColors(
                containerColor = Surface // Fondo blanco
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Envíos por mes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink // Color oscuro para el título
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "Últimos 6 meses, todas las sedes",
                    fontSize = 12.sp,
                    color = Muted // Color gris para el subtítulo
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Abril
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(105.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 6.dp,
                                        topEnd = 6.dp
                                    )
                                )
                                .background(
                                    Navy // Color Azul Marino para la barra
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Abr",
                            fontSize = 12.sp,
                            color = Muted // Color gris para las etiquetas
                        )
                    }


                    // Mayo
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(125.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 6.dp,
                                        topEnd = 6.dp
                                    )
                                )
                                .background(
                                    Navy // Color Azul Marino para la barra
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "May",
                            fontSize = 12.sp,
                            color = Muted // Color gris para las etiquetas
                        )
                    }


                    // Junio
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 6.dp,
                                        topEnd = 6.dp
                                    )
                                )
                                .background(
                                    Navy // Color Azul Marino para la barra
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Jun",
                            fontSize = 12.sp,
                            color = Muted // Color gris para las etiquetas
                        )
                    }


                    // Julio
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(145.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 6.dp,
                                        topEnd = 6.dp
                                    )
                                )
                                .background(
                                    Navy // Color Azul Marino para la barra
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Jul",
                            fontSize = 12.sp,
                            color = Muted // Color gris para las etiquetas
                        )
                    }


                    // Agosto
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 6.dp,
                                        topEnd = 6.dp
                                    )
                                )
                                .background(
                                    Navy // Color Azul Marino para la barra
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Ago",
                            fontSize = 12.sp,
                            color = Muted // Color gris para las etiquetas
                        )
                    }


                    // Septiembre
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 6.dp,
                                        topEnd = 6.dp
                                    )
                                )
                                .background(
                                    Orange // Color Naranja para la barra actual
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Sep",
                            fontSize = 12.sp,
                            color = Muted // Color gris para las etiquetas
                        )
                    }
                }
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // Muestra la cantidad de encomiendas por cada sede
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                Border // Color de borde sutil
            ),
            colors = CardDefaults.cardColors(
                containerColor = Surface // Fondo blanco
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Encomiendas por sede",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink // Color oscuro para el título
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // Sede Tarapoto
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Tarapoto",
                        fontSize = 14.sp,
                        color = Ink // Color oscuro para el nombre
                    )

                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy // Color Azul Marino para el número
                    )
                }

                HorizontalDivider(
                    color = Border // Color de borde sutil
                )


                // Sede Moyobamba
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Moyobamba",
                        fontSize = 14.sp,
                        color = Ink // Color oscuro para el nombre
                    )

                    Text(
                        text = "2",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy // Color Azul Marino para el número
                    )
                }

                HorizontalDivider(
                    color = Border // Color de borde sutil
                )


                // Sede Rioja
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Rioja",
                        fontSize = 14.sp,
                        color = Ink // Color oscuro para el nombre
                    )

                    Text(
                        text = "2",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy // Color Azul Marino para el número
                    )
                }

                HorizontalDivider(
                    color = Border // Color de borde sutil
                )


                // Sede Nueva Cajamarca
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Nueva Cajamarca",
                        fontSize = 14.sp,
                        color = Ink // Color oscuro para el nombre
                    )

                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy // Color Azul Marino para el número
                    )
                }

                HorizontalDivider(
                    color = Border // Color de borde sutil
                )


                // Sede Pomacochas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Pomacochas",
                        fontSize = 14.sp,
                        color = Ink // Color oscuro para el nombre
                    )

                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy // Color Azul Marino para el número
                    )
                }
            }
        }


        // Separación entre el módulo reportes y las incidencias
        Spacer(
            modifier = Modifier.height(36.dp)
        )

        HorizontalDivider(
            color = Border // Color de borde sutil
        )

        Spacer(
            modifier = Modifier.height(26.dp)
        )


        // Muestra las incidencias registradas
        Text(
            text = "Reportes de incidencias y técnicos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Ink // Color oscuro para el título
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Detalle de los problemas registrados durante el traslado",
            fontSize = 12.sp,
            color = Muted // Color gris para el subtítulo
        )


        Spacer(
            modifier = Modifier.height(18.dp)
        )


        // --- SECCIÓN MEJORADA: Tarjeta de la Primera Incidencia ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                Border // Color de borde sutil
            ),
            colors = CardDefaults.cardColors(
                containerColor = Surface // Fondo blanco
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // Fila superior: Tipo y Estado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Reporte técnico",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el título
                    )

                    // Etiqueta de estado
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Color(0xFFE53935).copy(alpha = 0.12f) // Rojo suave
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Resuelto",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFE53935) // Rojo
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha y Unidad
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Hoy 11:20",
                        fontSize = 12.sp,
                        color = Muted // Color gris
                    )
                    Text(
                        text = "Unidad: ABC-731",
                        fontSize = 12.sp,
                        color = Muted // Color gris
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Ubicación
                Text(
                    text = "Km 45, carretera Tarapoto–Moyobamba",
                    fontSize = 13.sp,
                    color = Ink, // Color oscuro
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botón de Ver Detalle
                OutlinedButton(
                    onClick = { onVerDetalleClick("ABC-731") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Navy), // Borde Azul Marino
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Navy // Texto Azul Marino
                    )
                ) {
                    Text(
                        text = "Ver detalle",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        // --- SECCIÓN MEJORADA: Tarjeta de la Segunda Incidencia ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                Border // Color de borde sutil
            ),
            colors = CardDefaults.cardColors(
                containerColor = Surface // Fondo blanco
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // Fila superior: Tipo y Estado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Paquete dañado",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink // Color oscuro para el título
                    )

                    // Etiqueta de estado
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Color(0xFFE53935).copy(alpha = 0.12f) // Rojo suave
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "En revisión",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFE53935) // Rojo
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha y Unidad
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Ayer 16:05",
                        fontSize = 12.sp,
                        color = Muted // Color gris
                    )
                    Text(
                        text = "Unidad: DEF-902",
                        fontSize = 12.sp,
                        color = Muted // Color gris
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Ubicación
                Text(
                    text = "—",
                    fontSize = 13.sp,
                    color = Ink, // Color oscuro
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botón de Ver Detalle
                OutlinedButton(
                    onClick = { onVerDetalleClick("DEF-902") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Navy), // Borde Azul Marino
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Navy // Texto Azul Marino
                    )
                ) {
                    Text(
                        text = "Ver detalle",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }


        // Espacio final de la pantalla
        Spacer(
            modifier = Modifier.height(32.dp)
        )

    }
}